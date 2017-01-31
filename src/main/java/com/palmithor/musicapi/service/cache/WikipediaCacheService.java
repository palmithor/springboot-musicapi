package com.palmithor.musicapi.service.cache;

import com.palmithor.musicapi.service.model.WikipediaResponse;
import org.springframework.stereotype.Component;

/**
 * Service for containing MusicBrainz API caching
 *
 * @author palmithor
 * @since 30.1.2017.
 */
@Component
public class WikipediaCacheService extends BaseCacheService<WikipediaResponse> {

    public static final String CACHE_KEY = "wikipedia";

    public WikipediaCacheService() {
        super(WikipediaResponse.class);
    }


    @Override
    String getCacheName() {
        return CACHE_KEY;
    }
}
