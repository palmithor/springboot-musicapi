package com.palmithor.musicapi.config;

import com.palmithor.musicapi.rest.artist.ArtistController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Cache configurations which are enabled when the profile dev is set
 * <p>
 * Provides beans wih ConcurrentMapCacheManager
 *
 * @author palmithor
 * @since 26.1.2017.
 */
@Configuration
@Profile("dev")
public class CacheConfigDev {

    private static final Logger log = LoggerFactory.getLogger(CacheConfigDev.class);

    @Bean
    public CacheManager concurrentMapCacheManager() {
        log.debug("Cache manager is concurrentMapCacheManager");
        return new ConcurrentMapCacheManager(ArtistController.CACHE_NAME);
    }

}
