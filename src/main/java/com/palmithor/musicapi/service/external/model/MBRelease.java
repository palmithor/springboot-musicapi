package com.palmithor.musicapi.service.external.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Model for Music Brainz release group
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class MBRelease {

    @SerializedName("id") private final String id;
    @SerializedName("primary-type-id") private final String primaryTypeId;
    @SerializedName("secondary-type-ids") private final List<String> secondaryTypeIds;
    @SerializedName("disambiguation") private final String disambiguation;
    @SerializedName("first-release-date") private final String firstReleaseDate;
    @SerializedName("primary-type") private final String primaryType;
    @SerializedName("title") private final String title;
    @SerializedName("secondary-types") private final List<String> secondaryTypes;

    public MBRelease() {
        this.id = null;
        this.primaryTypeId = null;
        this.secondaryTypeIds = null;
        this.disambiguation = null;
        this.firstReleaseDate = null;
        this.primaryType = null;
        this.title = null;
        this.secondaryTypes = null;
    }

    public MBRelease(final String id, final String primaryTypeId, final List<String> secondaryTypeIds, final String disambiguation, final String firstReleaseDate, final String primaryType, final String title, final List<String> secondaryTypes) {
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

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String id;
        private String primaryTypeId;
        private List<String> secondaryTypeIds;
        private String disambiguation;
        private String firstReleaseDate;
        private String primaryType;
        private String title;
        private List<String> secondaryTypes;

        private Builder() {
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withPrimaryTypeId(String primaryTypeId) {
            this.primaryTypeId = primaryTypeId;
            return this;
        }

        public Builder withSecondaryTypeIds(List<String> secondaryTypeIds) {
            this.secondaryTypeIds = secondaryTypeIds;
            return this;
        }

        public Builder withDisambiguation(String disambiguation) {
            this.disambiguation = disambiguation;
            return this;
        }

        public Builder withFirstReleaseDate(String firstReleaseDate) {
            this.firstReleaseDate = firstReleaseDate;
            return this;
        }

        public Builder withPrimaryType(String primaryType) {
            this.primaryType = primaryType;
            return this;
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withSecondaryTypes(List<String> secondaryTypes) {
            this.secondaryTypes = secondaryTypes;
            return this;
        }

        public MBRelease build() {
            return new MBRelease(id, primaryTypeId, secondaryTypeIds, disambiguation, firstReleaseDate, primaryType, title, secondaryTypes);
        }
    }
}
