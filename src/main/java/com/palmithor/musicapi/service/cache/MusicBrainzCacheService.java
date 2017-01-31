package com.palmithor.musicapi.service.cache;

import com.palmithor.musicapi.service.model.MusicBrainzArtistResponse;
import org.springframework.stereotype.Component;

/**
 * Service for containing MusicBrainz API caching
 *
 * @author palmithor
 * @since 30.1.2017.
 */
@Component
public class MusicBrainzCacheService extends BaseCacheService<MusicBrainzArtistResponse> {

    public static final String CACHE_KEY = "musicbrainz_artists";

    public MusicBrainzCacheService() {
        super(MusicBrainzArtistResponse.class);
    }


    @Override
    String getCacheName() {
        return CACHE_KEY;
    }
}
