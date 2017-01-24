package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Music Brainz release group
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class MBReleaseGroup {

    @SerializedName("id") private final String id;
    @SerializedName("primary-type-id") private final String primaryTypeId;
    @SerializedName("secondary-type-ids") private final List<String> secondaryTypeIds;
    @SerializedName("disambiguation") private final String disambiguation;
    @SerializedName("first-release-date") private final String firstReleaseDate;
    @SerializedName("primary-type") private final String primaryType;
    @SerializedName("title") private final String title;
    @SerializedName("secondary-types") private final List<String> secondaryTypes;

    public MBReleaseGroup() {
        this.id = null;
        this.primaryTypeId = null;
        this.secondaryTypeIds = null;
        this.disambiguation = null;
        this.firstReleaseDate = null;
        this.primaryType = null;
        this.title = null;
        this.secondaryTypes = null;
    }

    public MBReleaseGroup(final String id, final String primaryTypeId, final List<String> secondaryTypeIds, final String disambiguation, final String firstReleaseDate, final String primaryType, final String title, final List<String> secondaryTypes) {
        this.id = id;
        this.primaryTypeId = primaryTypeId;
        this.secondaryTypeIds = secondaryTypeIds;
        this.disambiguation = disambiguation;
        this.firstReleaseDate = firstReleaseDate;
        this.primaryType = primaryType;
        this.title = title;
        this.secondaryTypes = secondaryTypes;
    }

    public String getId() {
        return id;
    }

    public String getPrimaryTypeId() {
        return primaryTypeId;
    }

    public List<String> getSecondaryTypeIds() {
        return secondaryTypeIds;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public String getFirstReleaseDate() {
        return firstReleaseDate;
    }

    public String getPrimaryType() {
        return primaryType;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getSecondaryTypes() {
        return secondaryTypes;
    }
}
