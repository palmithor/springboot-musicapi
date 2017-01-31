package com.palmithor.musicapi.service.external;

/**
 * Should be thrown when API Services have an error
 *
 * @author palmithor
 * @since 30.1.2017.
 */
public class ExternalServiceException extends RuntimeException {

    private final Integer statusCode;

    public ExternalServiceException(final Integer statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String getMessage() {
        return "Status code: " + statusCode;
    }
}
