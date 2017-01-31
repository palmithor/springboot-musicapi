package com.palmithor.musicapi.service.cache;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.service.model.CoverArtResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Test to see if cache is working as it should
 *
 * @author palmithor
 * @since 31.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class CoverArtCacheServiceTest {

    @Autowired private CoverArtCacheService cacheService;


    @Test
    public void testPutGet() {
        final String key = "key";
        CoverArtResponse value = CoverArtResponse
                .createBuilder().withReleaseUrl("url").build();
        cacheService.put(key, value);
        CoverArtResponse fromCache = cacheService.get(key);
        assertThat(fromCache.getReleaseUrl(), is(value.getReleaseUrl()));
    }

    @Test
    public void testGetUnknown() {
        assertThat(cacheService.get("unknown"), is(nullValue()));
    }

}