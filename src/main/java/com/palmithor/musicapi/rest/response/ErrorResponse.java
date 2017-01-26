package com.palmithor.musicapi.rest.response;

/**
 * In the case of an error the API responds with this message
 *
 * @author palmithor
 * @since 13.1.2017.
 */
public class ErrorResponse extends BaseResponse {

    private ErrorResponse(Meta meta) {
        super(meta);
    }

    public static ErrorResponse create(final Integer code, final String message) {
        return new ErrorResponse(Meta.error(code, message));
    }
}
