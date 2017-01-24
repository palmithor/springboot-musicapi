package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model for Cover Art Archive Image Thumbnails
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class CoverArtArchiveThumbnails {

    @SerializedName("large") private final String largeImageUrl;
    @SerializedName("small") private final String smallImageUrl;

    public CoverArtArchiveThumbnails() {
        this.largeImageUrl = null;
        this.smallImageUrl = null;
    }

    public CoverArtArchiveThumbnails(final String largeImageUrl, final String smallImageUrl) {
        this.largeImageUrl = largeImageUrl;
        this.smallImageUrl = smallImageUrl;
    }

    public String getLargeImageUrl() {
        return largeImageUrl;
    }

    public String getSmallImageUrl() {
        return smallImageUrl;
    }
}
