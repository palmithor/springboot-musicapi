package com.palmithor.musicapi.service.external.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Cover Art Archive Image
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class CoverArtArchiveImage {

    @SerializedName("id") private final String id;
    @SerializedName("types") private final List<String> types;
    @SerializedName("front") private final Boolean front;
    @SerializedName("back") private final Boolean back;
    @SerializedName("edit") private final Long edit;
    @SerializedName("image") private final String url;
    @SerializedName("comment") private final String comment;
    @SerializedName("approved") private final Boolean approved;
    @SerializedName("thumbnails") private final CoverArtArchiveThumbnails thumbnails;


    public CoverArtArchiveImage() {
        this.id = null;
        this.types = null;
        this.front = null;
        this.back = null;
        this.edit = null;
        this.url = null;
        this.comment = null;
        this.approved = null;
        this.thumbnails = null;
    }

    public CoverArtArchiveImage(final String id, final List<String> types, final Boolean front, final Boolean back, final Long edit, final String url, final String comment, final Boolean approved, final CoverArtArchiveThumbnails thumbnails) {
        this.id = id;
        this.types = types;
        this.front = front;
        this.back = back;
        this.edit = edit;
        this.url = url;
        this.comment = comment;
        this.approved = approved;
        this.thumbnails = thumbnails;
    }

    public String getId() {
        return id;
    }

    public List<String> getTypes() {
        return types;
    }

    public Boolean getFront() {
        return front;
    }

    public Boolean getBack() {
        return back;
    }

    public Long getEdit() {
        return edit;
    }

    public String getUrl() {
        return url;
    }

    public String getComment() {
        return comment;
    }

    public Boolean getApproved() {
        return approved;
    }

    public CoverArtArchiveThumbnails getThumbnails() {
        return thumbnails;
    }
}
