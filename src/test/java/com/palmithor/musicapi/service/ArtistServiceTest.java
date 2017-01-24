package com.palmithor.musicapi.service;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.JsonTestUtils;
import com.palmithor.musicapi.TestConstants;
import com.palmithor.musicapi.dto.ArtistDTO;
import com.palmithor.musicapi.service.external.CoverArtArchiveService;
import com.palmithor.musicapi.service.external.MusicBrainzService;
import com.palmithor.musicapi.service.external.WikipediaService;
import com.palmithor.musicapi.service.external.model.CoverArtArchiveResponse;
import com.palmithor.musicapi.service.external.model.MBArtistResponse;
import com.palmithor.musicapi.service.external.model.WikipediaResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Response;
import rx.Observable;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyString;

/**
 * Unit tests for Artist service
 * <p>
 * All external services are mocked
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class ArtistServiceTest {


    @MockBean private MusicBrainzService musicBrainzService;
    @MockBean private CoverArtArchiveService coverArtArchiveService;
    @MockBean private WikipediaService wikipediaService;

    @Autowired private ArtistService artistService;

    @Test
    public void testFindByMusicBrainzIdSuccessful() throws FileNotFoundException {
        MBArtistResponse mbArtistResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.MUSIC_BRAINZ_ARTIST_RESPONSE, MBArtistResponse.class);
        WikipediaResponse wikipediaResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.WIKIPEDIA_RESPONSE, WikipediaResponse.class);
        CoverArtArchiveResponse coverArtArchiveResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.COVER_ART_ARCHIVE_RESPONSE, CoverArtArchiveResponse.class);

        given(this.musicBrainzService.getByMBId(anyString())).willReturn(Observable.just(Response.success(mbArtistResponse)));
        given(this.wikipediaService.get(anyString())).willReturn(Observable.just(Response.success(wikipediaResponse)));
        given(this.coverArtArchiveService.getByMBId(anyString())).willReturn(Observable.just(Response.success(coverArtArchiveResponse)));

        ArtistDTO artist = artistService.findByMusicBrainzId("id")
                .toBlocking()
                .first();

        assertThat(artist.getMbid(), is(mbArtistResponse.getId()));
        assertThat(artist.getDescription(), is(wikipediaResponse.getDescription()));
        assertThat(artist.getAlbums(), hasSize(25));
    }
}