package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Cover Art Archive response
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class CoverArtResponse {

    @SerializedName("release") private final String releaseUrl;
    @SerializedName("images") private final List<CoverArtArchiveImage> images;

    public CoverArtResponse() {
        this.releaseUrl = null;
        this.images = null;
    }

    public CoverArtResponse(final String releaseUrl, final List<CoverArtArchiveImage> images) {
        this.releaseUrl = releaseUrl;
        this.images = images;
    }

    public String getReleaseUrl() {
        return releaseUrl;
    }

    public List<CoverArtArchiveImage> getImages() {
        return images;
    }

    public String getImageUrl() {
        if (images != null && !images.isEmpty()) {
            return images.get(0).getUrl();
        }
        return null;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String releaseUrl;
        private List<CoverArtArchiveImage> images;

        private Builder() {
        }

        public Builder withReleaseUrl(String releaseUrl) {
            this.releaseUrl = releaseUrl;
            return this;
        }

        public Builder withImages(List<CoverArtArchiveImage> images) {
            this.images = images;
            return this;
        }

        public CoverArtResponse build() {
            return new CoverArtResponse(releaseUrl, images);
        }
    }
}
