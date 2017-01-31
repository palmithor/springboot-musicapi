package com.palmithor.musicapi.service.cache;

import com.palmithor.musicapi.service.model.CoverArtResponse;
import org.springframework.stereotype.Component;

/**
 * Service for containing MusicBrainz API caching
 *
 * @author palmithor
 * @since 30.1.2017.
 */
@Component
public class CoverArtCacheService extends BaseCacheService<CoverArtResponse> {

    public static final String CACHE_KEY = "cover_art";

    public CoverArtCacheService() {
        super(CoverArtResponse.class);
    }


    @Override
    String getCacheName() {
        return CACHE_KEY;
    }
}
