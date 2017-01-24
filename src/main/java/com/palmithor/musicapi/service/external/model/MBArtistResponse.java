package com.palmithor.musicapi.service.external.model;

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
    @SerializedName("release-groups") private final List<MBRelease> releases;
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
        this.releases = null;
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
                            final List<MBRelease> releases,
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
        this.releases = releases;
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

    public List<MBRelease> getReleases() {
        return releases;
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

    public boolean hasRelations() {
        return relations != null && !relations.isEmpty();
    }

    public boolean hasReleases() {
        return releases != null && !releases.isEmpty();
    }

    public static Builder createBuilder() {
        return new Builder();
    }


    public static final class Builder {
        private String id;
        private String name;
        private String sortName;
        private String typeId;
        private String country;
        private String gender;
        private String genderId;
        private String disambiguation;
        private String type;
        private List<MBArtistRelation> relations;
        private List<MBRelease> releaseGroups;
        private MBLifeSpan lifeSpan;
        private MBArea beginArea;
        private MBArea area;
        private List<String> isnis;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withSortName(String sortName) {
            this.sortName = sortName;
            return this;
        }

        public Builder withTypeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder withCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder withGender(String gender) {
            this.gender = gender;
            return this;
        }

        public Builder withGenderId(String genderId) {
            this.genderId = genderId;
            return this;
        }

        public Builder withDisambiguation(String disambiguation) {
            this.disambiguation = disambiguation;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withRelations(List<MBArtistRelation> relations) {
            this.relations = relations;
            return this;
        }

        public Builder withReleases(List<MBRelease> releaseGroups) {
            this.releaseGroups = releaseGroups;
            return this;
        }

        public Builder withLifeSpan(MBLifeSpan lifeSpan) {
            this.lifeSpan = lifeSpan;
            return this;
        }

        public Builder withBeginArea(MBArea beginArea) {
            this.beginArea = beginArea;
            return this;
        }

        public Builder withArea(MBArea area) {
            this.area = area;
            return this;
        }

        public Builder withIsnis(List<String> isnis) {
            this.isnis = isnis;
            return this;
        }

        public MBArtistResponse build() {
            return new MBArtistResponse(id, name, sortName, typeId, country, gender, genderId, disambiguation, type, relations, releaseGroups, lifeSpan, beginArea, area, isnis);
        }
    }
}
