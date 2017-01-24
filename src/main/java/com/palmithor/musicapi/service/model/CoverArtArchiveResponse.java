package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Cover Art Archive response
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class CoverArtArchiveResponse {

    @SerializedName("release") private final String releaseUrl;
    @SerializedName("images") private final List<CoverArtArchiveImage> images;

    public CoverArtArchiveResponse() {
        this.releaseUrl = null;
        this.images = null;
    }

    public CoverArtArchiveResponse(final String releaseUrl, final List<CoverArtArchiveImage> images) {
        this.releaseUrl = releaseUrl;
        this.images = images;
    }

    public String getReleaseUrl() {
        return releaseUrl;
    }

    public List<CoverArtArchiveImage> getImages() {
        return images;
    }
}
