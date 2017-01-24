package com.palmithor.musicapi.service.external.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Model for Wikipedia Query Object
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class WikipediaQuery {

    @SerializedName("pages") private Map<String, WikipediaPage> pages;

    public WikipediaQuery() {
        this.pages = null;
    }

    public WikipediaQuery(final Map<String, WikipediaPage> pages) {
        this.pages = pages;
    }

    public Map<String, WikipediaPage> getPages() {
        return pages;
    }
}

