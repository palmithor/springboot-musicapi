package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model for Music Brainz artist relation url
 *
 * @author palmithor
 * @since 23.1.2017.
 */
public class MBRelationUrl {

    @SerializedName("id") private final String id;
    @SerializedName("resource") private final String url;

    public MBRelationUrl() {
        this.id = null;
        this.url = null;
    }

    public MBRelationUrl(final String id, final String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
