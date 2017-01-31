package com.palmithor.musicapi.service;

import com.palmithor.musicapi.service.cache.CacheService;
import retrofit2.Response;
import rx.Observable;

/**
 * Base class for all services where data can be accessed via an external API
 * and should be cached
 *
 * @author palmithor
 * @since 31.1.2017.
 */
abstract class BaseCacheApiService<T> {

    private final CacheService<T> cacheService;

    BaseCacheApiService(final CacheService cacheService) {
        this.cacheService = cacheService;
    }

    Observable<T> getById(final String id) {
        return Observable.concat(fromCache(id), fromNetwork(id))
                .first();
    }

    private Observable<T> fromCache(final String id) {
        return cacheService.getAsObservable(id);
    }

    private Observable<T> fromNetwork(final String id) {
        return getApiServiceObservable(id)
                .flatMap(response -> {
                    if (response.isSuccessful()) {
                        cacheService.put(id, response.body());
                        return Observable.just(response.body());
                    }
                    throw createError(response);
                });
    }

    abstract RuntimeException createError(final Response<T> response);

    abstract Observable<Response<T>> getApiServiceObservable(final String id);
}
