package com.palmithor.musicapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmithor.musicapi.util.DateUtils;
import com.palmithor.musicapi.util.Rfc339DateJsonAdapter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Locale;

/**
 * Configuration module which defines utilities used application wide
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@Configuration
public class AppConfiguration {


    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setLocation(new ClassPathResource("application.properties"));
        return c;
    }

    @Bean
    public HttpMessageConverters customConverters() {

        Collection<HttpMessageConverter<?>> messageConverters = new ArrayList<>();

        GsonHttpMessageConverter gsonHttpMessageConverter = new GsonHttpMessageConverter();
        messageConverters.add(gsonHttpMessageConverter);

        return new HttpMessageConverters(true, messageConverters);
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(Date.class, new Rfc339DateJsonAdapter())
                .create();
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:locale/messages");
        messageSource.setCacheSeconds(DateUtils.HOUR_AS_SECONDS);
        return messageSource;
    }

}
