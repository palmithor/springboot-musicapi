package com.palmithor.musicapi.service.external.model;

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

    MBRelationUrl(final String id, final String url) {
        this.id = id;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String url;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public MBRelationUrl build() {
            return new MBRelationUrl(id, url);
        }
    }
}
