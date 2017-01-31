package com.palmithor.musicapi.service;

import com.palmithor.musicapi.service.cache.MusicBrainzCacheService;
import com.palmithor.musicapi.service.external.MusicBrainzAPIService;
import com.palmithor.musicapi.service.model.MusicBrainzArtistResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import rx.Observable;

/**
 * Service which fetches MusicBrainz artists from API and or fromCache.
 * <p>
 * If the record doesn't exist in the fromCache the API response is stored in it upon arrival
 *
 * @author palmithor
 * @since 30.1.2017.
 */
@Component
public class MusicBrainzService extends BaseCacheApiService<MusicBrainzArtistResponse> {

    private MusicBrainzAPIService apiService;

    @Autowired
    public MusicBrainzService(final MusicBrainzCacheService cacheService, final MusicBrainzAPIService apiService) {
        super(cacheService);
        this.apiService = apiService;
    }

    @Override
    Observable<Response<MusicBrainzArtistResponse>> getApiServiceObservable(final String id) {
        return apiService.getByMBId(id);
    }

    @Override
    RuntimeException createError(final Response<MusicBrainzArtistResponse> response) {
        switch (response.code()) {
            case 404:
                return new ServiceException(ServiceError.MUSIC_BRAINZ_ID_NOT_FOUND);
            case 400:
                return new ServiceException(ServiceError.MUSIC_BRAINZ_ID_INVALID);
            case 500:
                return new ServiceException(ServiceError.MUSIC_BRAINZ_INACCESSIBLE);
            default:
                return new ServiceException(ServiceError.INTERNAL_SERVER_ERROR);
        }
    }
}
