package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model for Music Brainz artist relation url
 *
 * @author palmithor
 * @since 23.1.2017.
 */
public class MusicBrainzRelationUrl implements Serializable {


    private static final long serialVersionUID = 5111945597674018503L;
    @SerializedName("id") private final String id;
    @SerializedName("resource") private final String url;

    public MusicBrainzRelationUrl() {
        this.id = null;
        this.url = null;
    }

    MusicBrainzRelationUrl(final String id, final String url) {
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

        public MusicBrainzRelationUrl build() {
            return new MusicBrainzRelationUrl(id, url);
        }
    }
}
