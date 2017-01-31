package com.palmithor.musicapi.service.external;

import com.palmithor.musicapi.service.model.CoverArtResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Retrofit service for accessing Cover Art Archive API
 *
 * @author palmithor
 * @since 23.1.2017.
 */
public interface CoverArtAPIService {


    @GET("release-group/{mbid}")
    Observable<Response<CoverArtResponse>> getByMusicBrainzReleaseId(@Path("mbid") final String mbid);

}
