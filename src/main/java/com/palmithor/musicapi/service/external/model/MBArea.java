package com.palmithor.musicapi.service.external.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Music Brainz area
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class MBArea {

    @SerializedName("id") private final String id;
    @SerializedName("sort-name") private final String sortName;
    @SerializedName("name") private final String name;
    @SerializedName("disambiguation") private final String disambiguation;
    @SerializedName("iso-3166-1-codes") private final List<String> isoCodes;

    public MBArea() {
        this.id = null;
        this.sortName = null;
        this.name = null;
        this.disambiguation = null;
        this.isoCodes = null;
    }

    public MBArea(final String id, final String sortName, final String name, final String disambiguation, final List<String> isoCodes) {
        this.id = id;
        this.sortName = sortName;
        this.name = name;
        this.disambiguation = disambiguation;
        this.isoCodes = isoCodes;
    }

    public String getId() {
        return id;
    }

    public String getSortName() {
        return sortName;
    }

    public String getName() {
        return name;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public List<String> getIsoCodes() {
        return isoCodes;
    }
}
