package com.palmithor.musicapi.service.external.model;

import com.google.gson.annotations.SerializedName;
import org.springframework.util.StringUtils;

/**
 * Model for Music Brainz artist relation
 *
 * @author palmithor
 * @since 23.1.2017.
 */
public class MBArtistRelation {


    @SerializedName("ended") private final Boolean ended;
    @SerializedName("type-id") private final String typeId;
    @SerializedName("type") private final String type;
    @SerializedName("direction") private final String direction;
    @SerializedName("url") private final MBRelationUrl url;
    @SerializedName("target-type") private final String targetType;
    @SerializedName("target-credit") private final String targetCredit;
    @SerializedName("source-credit") private final String sourceCredit;


    public MBArtistRelation() {
        this.ended = null;
        this.typeId = null;
        this.type = null;
        this.direction = null;
        this.url = null;
        this.targetType = null;
        this.targetCredit = null;
        this.sourceCredit = null;
    }

    public MBArtistRelation(final Boolean ended, final String typeId, final String type, final String direction, final MBRelationUrl url, final String targetType, final String targetCredit, final String sourceCredit) {
        this.ended = ended;
        this.typeId = typeId;
        this.type = type;
        this.direction = direction;
        this.url = url;
        this.targetType = targetType;
        this.targetCredit = targetCredit;
        this.sourceCredit = sourceCredit;
    }

    public Boolean getEnded() {
        return ended;
    }

    public String getTypeId() {
        return typeId;
    }

    public String getType() {
        return type;
    }

    public String getDirection() {
        return direction;
    }

    public MBRelationUrl getUrl() {
        return url;
    }

    public String getTargetType() {
        return targetType;
    }

    public String getTargetCredit() {
        return targetCredit;
    }

    public String getSourceCredit() {
        return sourceCredit;
    }

    public boolean hasUrl() {
        return url != null && !StringUtils.isEmpty(url.getUrl());
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private Boolean ended;
        private String typeId;
        private String type;
        private String direction;
        private MBRelationUrl url;
        private String targetType;
        private String targetCredit;
        private String sourceCredit;

        private Builder() {
        }

        public Builder withEnded(Boolean ended) {
            this.ended = ended;
            return this;
        }

        public Builder withTypeId(String typeId) {
            this.typeId = typeId;
            return this;
        }

        public Builder withType(String type) {
            this.type = type;
            return this;
        }

        public Builder withDirection(String direction) {
            this.direction = direction;
            return this;
        }

        public Builder withUrl(MBRelationUrl url) {
            this.url = url;
            return this;
        }

        public Builder withTargetType(String targetType) {
            this.targetType = targetType;
            return this;
        }

        public Builder withTargetCredit(String targetCredit) {
            this.targetCredit = targetCredit;
            return this;
        }

        public Builder withSourceCredit(String sourceCredit) {
            this.sourceCredit = sourceCredit;
            return this;
        }

        public MBArtistRelation build() {
            return new MBArtistRelation(ended, typeId, type, direction, url, targetType, targetCredit, sourceCredit);
        }
    }
}
