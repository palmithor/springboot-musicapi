package com.palmithor.musicapi.service.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import rx.Observable;

/**
 * Base class used to extract code for cache services
 *
 * @author palmithor
 * @since 30.1.2017.
 */
public abstract class BaseCacheService<T> implements CacheService<T> {

    @Autowired private CacheManager cacheManager;

    private final Class<T> clazz;

    BaseCacheService(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public Observable<T> getAsObservable(final String key) {
        return Observable.create(subscriber -> {
            T objectResponse = get(key);
            if (objectResponse != null) {
                subscriber.onNext(objectResponse);
            }
            subscriber.onCompleted();
        });
    }

    public T get(final String key) {
        return getCache().get(key, clazz);
    }

    private Cache getCache() {
        return cacheManager.getCache(getCacheName());
    }

    public void put(final String id, final T data) {
        getCache().put(id, data);
    }

    /**
     * The name of the cache.
     * <p>
     * Must map to a record in ehcache.xml
     *
     * @return the name of the cache, configured in ehcache
     */
    abstract String getCacheName();
}
