package com.palmithor.musicapi.service;

import com.google.gson.Gson;
import com.palmithor.musicapi.AppProfiles;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Arrays;

/**
 * Spring Boot configuration for providing Retrofit services
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@Configuration
public class ServiceConfiguration {

    @Autowired
    private Environment environment;


    @Bean
    public MusicBrainzService provideMusicBrainzService(final OkHttpClient client, final Gson gson) {
        final String serviceBaseUrl = "http://musicbrainz.org/";
        return createRetrofitInstance(client, gson, serviceBaseUrl).create(MusicBrainzService.class);
    }

    @Bean
    public CoverArtArchiveService provideCoverArtArchiveService(final OkHttpClient client, final Gson gson) {
        final String serviceBaseUrl = "http://coverartarchive.org/";
        return createRetrofitInstance(client, gson, serviceBaseUrl).create(CoverArtArchiveService.class);
    }

    @Bean
    public WikipediaService provideWikipediaService(final OkHttpClient client, final Gson gson) {
        Retrofit retrofit = createRetrofitInstance(client, gson, "https://en.wikipedia.org/w/api.php/");
        return retrofit.create(WikipediaService.class);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (!Arrays.stream(environment.getActiveProfiles()).anyMatch(
                env -> (env.equalsIgnoreCase(AppProfiles.PRODUCTION)))) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            clientBuilder.addInterceptor(interceptor);
        }
        return clientBuilder.build();
    }

    private Retrofit createRetrofitInstance(final OkHttpClient client, final Gson gson, final String serviceBaseUrl) {
        return new Retrofit.Builder()
                .baseUrl(serviceBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();
    }
}
