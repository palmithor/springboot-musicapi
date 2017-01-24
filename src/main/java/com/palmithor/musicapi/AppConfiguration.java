package com.palmithor.musicapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.palmithor.musicapi.util.Rfc339DateJsonAdapter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Configuration module which defines utilities used application wide
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@Configuration
public class AppConfiguration {

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
}
