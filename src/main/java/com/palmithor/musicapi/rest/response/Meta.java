package com.palmithor.musicapi.rest.response;


/**
 * @author palmithor
 * @since 13.1.2017.
 */
public class Meta {

    private static final int CODE_SUCCESS = 20000;

    private final Integer code;
    private final String message;

    private Meta() {
        this.code = null;
        this.message = null;
    }

    private Meta(final Integer code, final String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    static Meta success(final String message) {
        return new Meta(CODE_SUCCESS, message);
    }

    static Meta error(final Integer code, final String message) {
        return new Meta(code, message);
    }

    public int getStatusCode() {
        final String codeAsStr = String.valueOf(code);
        return Integer.valueOf(codeAsStr.substring(0, 3));
    }
}
