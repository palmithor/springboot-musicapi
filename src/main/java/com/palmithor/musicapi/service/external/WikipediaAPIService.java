package com.palmithor.musicapi.service.external;

import com.palmithor.musicapi.service.model.WikipediaResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Retrofit service for accessing Wikipedia API
 *
 * @author palmithor
 * @since 23.1.2017.
 */
public interface WikipediaAPIService {


    /**
     * Retrofit definitions of Wikipedia API for getting a page by title
     * <p>
     * It is not ideal to have constant query parameters
     * but since it is the only use case, for now, no support
     * is for configuring some query params
     *
     * @param title the wikipedia page title to serach for
     * @return Observable Retrofit HTTP response which wraps a WikipediaResponse object
     */
    @GET("/w/api.php?action=query&format=json&prop=extracts&exintro=true&redirects=true")
    Observable<Response<WikipediaResponse>> get(@Query("titles") final String title);


}
