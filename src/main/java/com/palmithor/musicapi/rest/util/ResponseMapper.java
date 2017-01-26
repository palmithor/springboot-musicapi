package com.palmithor.musicapi.rest.util;

import com.palmithor.musicapi.rest.response.ErrorResponse;
import com.palmithor.musicapi.rest.response.ObjectResponse;
import com.palmithor.musicapi.service.MessageByLocaleService;
import com.palmithor.musicapi.service.ServiceError;
import com.palmithor.musicapi.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;
import rx.Observable;

/**
 * Base class for mapping responses and requests
 *
 * @author palmithor
 * @since 25.1.2017.
 */
abstract class ResponseMapper<T> {

    @Autowired MessageByLocaleService messageByLocaleService;

    @Value("${com.palmithor.musicapi.timeout:60000}")
    private Long timeout;

    public DeferredResult<ResponseEntity<ObjectResponse<T>>> mapObjectResponse(final Observable<T> observable) {
        DeferredResult<ResponseEntity<ObjectResponse<T>>> deferred = new DeferredResult<>(timeout);
        observable.subscribe(
                obj -> deferred.setResult(ResponseEntity.ok(new ObjectResponse<>(obj, messageByLocaleService.getMessage(MessageByLocaleService.Message.SUCCESSFUL_OK)))),
                throwable -> {
                    ErrorResponse errorResponse = mapThrowableToError(throwable);
                    deferred.setErrorResult(ResponseEntity
                            .status(errorResponse.getMeta().getStatusCode())
                            .body(errorResponse));
                });
        return deferred;
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
