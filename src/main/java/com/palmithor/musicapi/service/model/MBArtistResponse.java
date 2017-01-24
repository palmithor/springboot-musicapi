package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Music Brainz artist response
 *
 * @author palmithor
 * @since 23.1.2017.
 */
public class MBArtistResponse {

    @SerializedName("id") private final String id;
    @SerializedName("name") private final String name;
    @SerializedName("sort-name") private final String sortName;
    @SerializedName("type-id") private final String typeId;
    @SerializedName("country") private final String country;
    @SerializedName("gender") private final String gender;
    @SerializedName("gender-id") private final String genderId;
    @SerializedName("disambiguation") private final String disambiguation;
    @SerializedName("type") private final String type;
    @SerializedName("relations") private final List<MBArtistRelation> relations;
    @SerializedName("release-groups") private final List<MBReleaseGroup> releaseGroups;
    @SerializedName("life-span") private final MBLifeSpan lifeSpan;
    @SerializedName("begin_area") private final MBArea beginArea;
    @SerializedName("area") private final MBArea area;
    @SerializedName("isnis") private final List<String> isnis;

    public MBArtistResponse() {
        this.id = null;
        this.name = null;
        this.sortName = null;
        this.typeId = null;
        this.country = null;
        this.gender = null;
        this.genderId = null;
        this.disambiguation = null;
        this.type = null;
        this.relations = null;
        this.releaseGroups = null;
        this.lifeSpan = null;
        this.beginArea = null;
        this.area = null;
        this.isnis = null;
    }

    public MBArtistResponse(final String id,
                            final String name,
                            final String sortName,
                            final String typeId,
                            final String country,
                            final String gender,
                            final String genderId,
                            final String disambiguation,
                            final String type,
                            final List<MBArtistRelation> relations,
                            final List<MBReleaseGroup> releaseGroups,
                            final MBLifeSpan lifeSpan, final MBArea beginArea, final MBArea area, final List<String> isnis) {
        this.id = id;
        this.name = name;
        this.sortName = sortName;
        this.typeId = typeId;
        this.country = country;
        this.gender = gender;
        this.genderId = genderId;
        this.disambiguation = disambiguation;
        this.type = type;
        this.relations = relations;
        this.releaseGroups = releaseGroups;
        this.lifeSpan = lifeSpan;
        this.beginArea = beginArea;
        this.area = area;
        this.isnis = isnis;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSortName() {
        return sortName;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getCountry() {
        return country;
    }

    public String getGender() {
        return gender;
    }

    public String getGenderId() {
        return genderId;
    }

    public String getDisambiguation() {
        return disambiguation;
    }

    public String getType() {
        return type;
    }

    public List<MBArtistRelation> getRelations() {
        return relations;
    }

    public List<MBReleaseGroup> getReleaseGroups() {
        return releaseGroups;
    }

    public MBLifeSpan getLifeSpan() {
        return lifeSpan;
    }

    public MBArea getBeginArea() {
        return beginArea;
    }

    public MBArea getArea() {
        return area;
    }

    public List<String> getIsnis() {
        return isnis;
    }
}
