package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

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
    @SerializedName("url") private final MBRelationUrl relation;
    @SerializedName("target-type") private final String targetType;
    @SerializedName("target-credit") private final String targetCredit;
    @SerializedName("source-credit") private final String sourceCredit;


    public MBArtistRelation() {
        this.ended = null;
        this.typeId = null;
        this.type = null;
        this.direction = null;
        this.relation = null;
        this.targetType = null;
        this.targetCredit = null;
        this.sourceCredit = null;
    }

    public MBArtistRelation(final Boolean ended, final String typeId, final String type, final String direction, final MBRelationUrl relation, final String targetType, final String targetCredit, final String sourceCredit) {
        this.ended = ended;
        this.typeId = typeId;
        this.type = type;
        this.direction = direction;
        this.relation = relation;
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

    public MBRelationUrl getRelation() {
        return relation;
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
}
