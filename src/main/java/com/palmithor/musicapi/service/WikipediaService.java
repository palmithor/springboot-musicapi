package com.palmithor.musicapi.service;

import com.palmithor.musicapi.service.cache.WikipediaCacheService;
import com.palmithor.musicapi.service.external.ExternalServiceException;
import com.palmithor.musicapi.service.external.WikipediaAPIService;
import com.palmithor.musicapi.service.model.WikipediaResponse;
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
public class WikipediaService extends BaseCacheApiService<WikipediaResponse> {

    private WikipediaAPIService apiService;

    @Autowired
    public WikipediaService(final WikipediaCacheService cacheService, final WikipediaAPIService apiService) {
        super(cacheService);
        this.apiService = apiService;
    }

    @Override
    RuntimeException createError(final Response<WikipediaResponse> response) {
        return new ExternalServiceException(response.code());
    }

    @Override
    Observable<Response<WikipediaResponse>> getApiServiceObservable(final String id) {
        return apiService.get(id);
    }
}
