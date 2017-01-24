package com.palmithor.musicapi.service;

/**
 * An exception that wraps errors that occur in services
 * <p>
 * Contains all information needed for the Controllers to createBuilder an error response
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class ServiceException extends RuntimeException {

    final ServiceError serviceError;

    ServiceException(final ServiceError serviceError) {
        this.serviceError = serviceError;
    }

    ServiceException(final ServiceError serviceError, Throwable cause) {
        super(cause);
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError() {
        return serviceError;
    }
}
