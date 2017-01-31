package com.palmithor.musicapi.service;

import com.google.gson.Gson;
import com.palmithor.musicapi.AppProfiles;
import com.palmithor.musicapi.service.external.CoverArtAPIService;
import com.palmithor.musicapi.service.external.MusicBrainzAPIService;
import com.palmithor.musicapi.service.external.WikipediaAPIService;
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

    @Autowired private Environment environment;


    @Bean
    public MusicBrainzAPIService musicBrainzAPIService(final OkHttpClient client, final Gson gson) {
        final String serviceBaseUrl = "http://musicbrainz.org/";
        return createRetrofitInstance(client, gson, serviceBaseUrl).create(MusicBrainzAPIService.class);
    }

    @Bean
    public CoverArtAPIService coverArtAPIService(final OkHttpClient client, final Gson gson) {
        final String serviceBaseUrl = "http://coverartarchive.org/";
        return createRetrofitInstance(client, gson, serviceBaseUrl).create(CoverArtAPIService.class);
    }

    @Bean
    public WikipediaAPIService wikipediaAPIService(final OkHttpClient client, final Gson gson) {
        Retrofit retrofit = createRetrofitInstance(client, gson, "https://en.wikipedia.org/w/api.php/");
        return retrofit.create(WikipediaAPIService.class);
    }

    @Bean
    public OkHttpClient okHttpClient() {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        if (Arrays.stream(environment.getActiveProfiles()).noneMatch(
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
