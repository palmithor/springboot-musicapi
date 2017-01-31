package com.palmithor.musicapi.service.cache;

import rx.Observable;

/**
 * Interface to allow for injection of different implementations of caching services
 *
 * @author palmithor
 * @since 31.1.2017.
 */
public interface CacheService<T> {

    Observable<T> getAsObservable(final String key);

    T get(final String key);

    void put(final String key, final T data);
}
