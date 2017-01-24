package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model for Wikipedia Page Container
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class WikipediaParent {


    @SerializedName("batchcomplete") private String batchComplete;
    @SerializedName("query") private WikipediaQuery query;

    public WikipediaParent() {
        this.batchComplete = null;
        this.query = null;
    }

    public WikipediaParent(final String batchComplete, final WikipediaQuery query) {
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

