package com.palmithor.musicapi.service.external;

import com.palmithor.musicapi.service.external.model.CoverArtArchiveResponse;
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
public interface CoverArtArchiveService {


    @GET("release-group/{mbid}")
    Observable<Response<CoverArtArchiveResponse>> getByMBId(@Path("mbid") final String mbid);

}
