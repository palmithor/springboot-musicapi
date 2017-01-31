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

    public String getDescription() {
        String result = null;
        if (query != null && query.getPages() != null && !query.getPages().isEmpty()) {
            WikipediaPage wikipediaPage = query.getPages().values().iterator().next();
            result = wikipediaPage.getDescription();
        }
        return result;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String batchComplete;
        private WikipediaQuery query;

        private Builder() {
        }

        public Builder withBatchComplete(String batchComplete) {
            this.batchComplete = batchComplete;
            return this;
        }

        public Builder withQuery(WikipediaQuery query) {
            this.query = query;
            return this;
        }

        public WikipediaResponse build() {
            WikipediaResponse wikipediaResponse = new WikipediaResponse(batchComplete, query);
            return wikipediaResponse;
        }
    }
}

