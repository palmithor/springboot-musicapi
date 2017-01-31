package com.palmithor.musicapi.service;

import com.palmithor.musicapi.service.cache.CoverArtCacheService;
import com.palmithor.musicapi.service.external.CoverArtAPIService;
import com.palmithor.musicapi.service.external.ExternalServiceException;
import com.palmithor.musicapi.service.model.CoverArtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import rx.Observable;

/**
 * Service which fetches Cover Art from API and or from Cache.
 * <p>
 * If the record doesn't exist in the cache the API response is stored in it upon arrival
 *
 * @author palmithor
 * @since 30.1.2017.
 */
@Component
public class CoverArtService extends BaseCacheApiService<CoverArtResponse> {

    private CoverArtAPIService apiService;

    @Autowired
    public CoverArtService(final CoverArtCacheService cacheService, final CoverArtAPIService apiService) {
        super(cacheService);
        this.apiService = apiService;
    }

    @Override
    RuntimeException createError(final Response<CoverArtResponse> response) {
        return new ExternalServiceException(response.code());
    }

    @Override
    Observable<Response<CoverArtResponse>> getApiServiceObservable(final String id) {
        return apiService.getByMusicBrainzReleaseId(id);
    }
}
