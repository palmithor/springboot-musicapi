package com.palmithor.musicapi.service.model;

import com.google.gson.annotations.SerializedName;

/**
 * Model for Wikipedia Page
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class WikipediaPage {

    @SerializedName("pageid") private final Integer pageId;
    @SerializedName("ns") private final Integer ns;
    @SerializedName("title") private final String title;
    @SerializedName("extract") private final String description;


    public WikipediaPage() {
        this.pageId = null;
        this.ns = null;
        this.title = null;
        this.description = null;
    }

    public WikipediaPage(final Integer pageId, final Integer ns, final String title, final String description) {
        this.pageId = pageId;
        this.ns = ns;
        this.title = title;
        this.description = description;
    }

    public Integer getPageId() {
        return pageId;
    }

    public Integer getNs() {
        return ns;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}

