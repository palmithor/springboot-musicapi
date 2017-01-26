package com.palmithor.musicapi.service.external.model;

import com.google.gson.annotations.SerializedName;

/**
 * MusicBrainz error response
 * <p>
 * Contains error messages about why the request was unsuccessful
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public  class MBErrorResponse {

    @SerializedName("error") private final String errorMessage;

    public MBErrorResponse() {
        this.errorMessage = null;
    }

    public MBErrorResponse(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
