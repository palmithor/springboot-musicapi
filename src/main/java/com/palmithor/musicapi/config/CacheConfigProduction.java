package com.palmithor.musicapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;

/**
 * Cache configurations which are enabled when the profile production is set
 * <p>
 * Provides beans wih EhCachCacheManager
 *
 * @author palmithor
 * @since 26.1.2017.
 */
@Configuration
@Profile("production")
public class CacheConfigProduction {

    private static final Logger log = LoggerFactory.getLogger(CacheConfigProduction.class);

    @Bean
    public CacheManager cacheManager() {
        log.debug("Cache manager is ehCacheCacheManager");
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }

}
