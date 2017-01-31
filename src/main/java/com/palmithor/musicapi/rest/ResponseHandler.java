package com.palmithor.musicapi.rest;

import com.palmithor.musicapi.rest.response.ErrorResponse;
import com.palmithor.musicapi.rest.response.ObjectResponse;
import com.palmithor.musicapi.service.MessageByLocaleService;
import com.palmithor.musicapi.service.ServiceError;
import com.palmithor.musicapi.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;

/**
 * Base class for mapping responses and requests
 *
 * @author palmithor
 * @since 25.1.2017.
 */
public abstract class ResponseHandler<T> {

    @Autowired private MessageByLocaleService messageByLocaleService;
    @Autowired private CacheManager cacheManager;

    @Value("${com.palmithor.musicapi.timeout:60000}")
    private Long timeout;

    private final Class<T> clazz;

    public ResponseHandler(final Class<T> clazz) {
        this.clazz = clazz;
    }

    public DeferredResult<ResponseEntity<ObjectResponse<T>>> mapObjectResponse(final String cacheName,
                                                                               final String cacheKey,
                                                                               final Observable<T> observable) {
        DeferredResult<ResponseEntity<ObjectResponse<T>>> deferred = new DeferredResult<>(timeout);

        T fromCache = cacheManager.getCache(cacheName).get(cacheKey, clazz);
        if (fromCache != null) {
            setResultsOK(deferred, fromCache);
        } else {
            observable.subscribe(
                    obj -> {
                        if (shouldCache(obj)) {
                            cacheManager.getCache(cacheName).put(cacheKey, obj);
                        }
                        setResultsOK(deferred, obj);
                    },
                    throwable -> {
                        ErrorResponse errorResponse = mapThrowableToError(throwable);
                        deferred.setErrorResult(ResponseEntity
                                .status(errorResponse.getMeta().getStatusCode())
                                .body(errorResponse));
                    });
        }
        return deferred;
    }

    protected abstract boolean shouldCache(final T obj);

    private void setResultsOK(final DeferredResult<ResponseEntity<ObjectResponse<T>>> deferred, final T obj) {
        deferred.setResult(
                ResponseEntity.ok(
                        new ObjectResponse<>(obj,
                                messageByLocaleService
                                        .getMessage(MessageByLocaleService.Message.SUCCESSFUL_OK))
                ));
    }


    private ErrorResponse mapThrowableToError(final Throwable throwable) {
        if (throwable instanceof ServiceException) {
            ServiceError serviceError = ((ServiceException) throwable).getServiceError();
            return ErrorResponse.create(serviceError.getCode(), messageByLocaleService.getMessage(serviceError.getMessageKey()));
        }
        return ErrorResponse.create(ServiceError.INTERNAL_SERVER_ERROR.getCode(),
                messageByLocaleService.getMessage(ServiceError.INTERNAL_SERVER_ERROR.getMessageKey()));
    }
}
