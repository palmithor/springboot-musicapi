package com.palmithor.musicapi.service;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.dto.ArtistDto;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileNotFoundException;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Class only contains one test which should be ignored as it depends on the three APIs used in the
 * ArtistService which is not good to include in the default test suite.
 * <p>
 * Test was mainly created to see performance and if more parallelism was needed.
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ArtistServiceIT {


    @Autowired private ArtistService artistService;

    @Test
    @Ignore
    public void testFindByMusicBrainzIdSuccessful() throws FileNotFoundException {
        long start = System.currentTimeMillis();
        ArtistDto artist = artistService.findByMusicBrainzId("5b11f4ce-a62d-471e-81fc-a69a8278c7da")
                .toBlocking()
                .first();

        long total = System.currentTimeMillis() - start;
        LoggerFactory.getLogger(ArtistServiceIT.class).info("Total response time for all findByMusicBrainzId(): " + total);

        assertThat(artist.getAlbums(), hasSize(25));
    }
}