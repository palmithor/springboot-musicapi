package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model for Wikipedia response
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class WikipediaResponse {


    @SerializedName("batchcomplete") private String batchComplete;
    @SerializedName("query") private WikipediaQuery query;

    public WikipediaResponse() {
        this.batchComplete = null;
        this.query = null;
    }

    public WikipediaResponse(final String batchComplete, final WikipediaQuery query) {
        this.batchComplete = batchComplete;
        this.query = query;
    }

    public String getBatchComplete() {
        return batchComplete;
    }

    public WikipediaQuery getQuery() {
        return query;
    }
}

