package com.palmithor.musicapi.service;

/**
 * An enum with different error codes, message reference identifier, internal code and http status code
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public enum ServiceError {

    MUSIC_BRAIN_ID_NOT_FOUND(40401, MessageByLocaleService.Errors.MBID_NOT_FOUND),

    MUSIC_BRAIN_ID_INVALID(40001, MessageByLocaleService.Errors.MBID_INVALID),
    INTERNAL_SERVER_ERROR(50001, MessageByLocaleService.Errors.INTERNAL_SERVER_ERROR);

    private final int code;
    private final String messageKey;

    ServiceError(final int code, final String messageKey) {
        this.code = code;
        this.messageKey = messageKey;
    }

    public int getCode() {
        return code;
    }

    public String getMessageKey() {
        return messageKey;
    }
}
