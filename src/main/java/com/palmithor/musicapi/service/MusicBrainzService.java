package com.palmithor.musicapi.service;


import com.palmithor.musicapi.service.model.MBArtistResponse;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;


/**
 * Retrofit service for accessing MusicBrainz API
 *
 * @author palmithor
 * @since 23.1.2017.
 */
public interface MusicBrainzService {


    /**
     * Retrofit definitions of MusicBrainz API for getting an artist by id.
     * <p>
     * It is not ideal to have constant query parameters
     * but since it is the only use case, for now, no support
     * is for configuring some query params
     *
     * @param mbid the MusicBrainz identifier, format is UUID
     * @return Observable Retrofit HTTP response which wraps a MBArtistResponse object
     */
    @GET("/ws/2/artist/{mbid}?fmt=json&inc=url-rels+release-groups")
    Observable<Response<MBArtistResponse>> getByMBId(@Path("mbid") final String mbid);

}
