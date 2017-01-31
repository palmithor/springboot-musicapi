package com.palmithor.musicapi.service;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.JsonTestUtils;
import com.palmithor.musicapi.TestConstants;
import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.service.cache.CoverArtCacheService;
import com.palmithor.musicapi.service.cache.MusicBrainzCacheService;
import com.palmithor.musicapi.service.cache.WikipediaCacheService;
import com.palmithor.musicapi.service.external.CoverArtAPIService;
import com.palmithor.musicapi.service.external.ExternalServiceException;
import com.palmithor.musicapi.service.external.MusicBrainzAPIService;
import com.palmithor.musicapi.service.external.WikipediaAPIService;
import com.palmithor.musicapi.service.model.CoverArtResponse;
import com.palmithor.musicapi.service.model.MusicBrainzArtistResponse;
import com.palmithor.musicapi.service.model.MusicBrainzRelease;
import com.palmithor.musicapi.service.model.WikipediaResponse;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Response;
import rx.Observable;
import rx.observers.TestSubscriber;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
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


    @MockBean private MusicBrainzAPIService musicBrainzAPIService;
    @MockBean private CoverArtAPIService coverArtAPIService;
    @MockBean private WikipediaAPIService wikipediaAPIService;


    @Autowired private MusicBrainzCacheService musicBrainzCacheService;
    @Autowired private CoverArtCacheService coverArtCacheService;
    @Autowired private WikipediaCacheService wikipediaCacheService;
    @Autowired private ArtistService artistService;

    @Test
    public void testFindByMusicBrainzIdSuccessful() throws FileNotFoundException {
        MusicBrainzArtistResponse musicBrainzArtistResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.MUSIC_BRAINZ_ARTIST_RESPONSE, MusicBrainzArtistResponse.class);
        WikipediaResponse wikipediaResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.WIKIPEDIA_RESPONSE, WikipediaResponse.class);
        CoverArtResponse coverArtResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.COVER_ART_ARCHIVE_RESPONSE, CoverArtResponse.class);

        given(musicBrainzAPIService.getByMBId(anyString()))
                .willReturn(Observable.just(Response.success(musicBrainzArtistResponse)));
        given(wikipediaAPIService.get(anyString()))
                .willReturn(Observable.just(Response.success(wikipediaResponse)));
        given(coverArtAPIService.getByMusicBrainzReleaseId(any()))
                .willReturn(Observable.just(Response.success(coverArtResponse)));

        TestSubscriber<ArtistDto> testSubscriber = new TestSubscriber<>();

        String mbid = UUID.randomUUID().toString();
        artistService.findByMusicBrainzId(mbid)
                .subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertCompleted();
        List<ArtistDto> onNextEvents = testSubscriber.getOnNextEvents();
        assertThat(onNextEvents, hasSize(1));
        ArtistDto artist = onNextEvents.get(0);

        assertThat(artist.getMbid(), is(musicBrainzArtistResponse.getId()));
        assertThat(artist.getDescription(), is(wikipediaResponse.getDescription()));
        assertThat(artist.getAlbums(), hasSize(25));

        assertThat(musicBrainzCacheService.get(mbid), is(notNullValue()));
        assertThat(wikipediaCacheService.get("Nirvana_(band)"), is(notNullValue()));
        for (MusicBrainzRelease release : musicBrainzArtistResponse.getReleases()) {
            assertThat(release.getId(), is(notNullValue()));
        }
    }

    /**
     * This test takes long due to retries
     */
    @Test
    public void testFindByMusicBrainzIdNotFound() throws FileNotFoundException {

        given(musicBrainzAPIService.getByMBId(anyString()))
                .willReturn(Observable.just(Response.error(404, ResponseBody.create(MediaType.parse("application/json"), "{}"))));

        TestSubscriber<ArtistDto> testSubscriber = new TestSubscriber<>();

        artistService.findByMusicBrainzId(UUID.randomUUID().toString()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertError(ServiceException.class);
        List<Throwable> onErrorEvents = testSubscriber.getOnErrorEvents();
        assertThat(onErrorEvents, hasSize(1));
        assertThat(((ServiceException) onErrorEvents.get(0)).getServiceError(), is(ServiceError.MUSIC_BRAINZ_ID_NOT_FOUND));
    }


    @Test
    public void testFindByMusicBrainzIdWithoutWikipediaRelation() throws FileNotFoundException {

        MusicBrainzArtistResponse mockResponse = MusicBrainzArtistResponse.createBuilder()
                .withId(UUID.randomUUID().toString()).withName("Band Name").build();
        given(musicBrainzAPIService.getByMBId(anyString()))
                .willReturn(Observable.just(Response.success(mockResponse)));

        TestSubscriber<ArtistDto> testSubscriber = new TestSubscriber<>();

        artistService.findByMusicBrainzId(UUID.randomUUID().toString()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        List<ArtistDto> onNextEvents = testSubscriber.getOnNextEvents();
        assertThat(onNextEvents, hasSize(1));
        ArtistDto artist = onNextEvents.get(0);

        assertThat(artist.getDescription(), is(nullValue()));
        assertThat(artist.getAlbums(), hasSize(0));
    }

    @Test
    public void testFindByMusicBrainzIdWikipediaError() throws FileNotFoundException {

        MusicBrainzArtistResponse musicBrainzArtistResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.MUSIC_BRAINZ_ARTIST_RESPONSE, MusicBrainzArtistResponse.class);
        CoverArtResponse coverArtResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.COVER_ART_ARCHIVE_RESPONSE, CoverArtResponse.class);

        given(coverArtAPIService.getByMusicBrainzReleaseId(any()))
                .willReturn(Observable.just(Response.success(coverArtResponse)));
        given(musicBrainzAPIService.getByMBId(anyString()))
                .willReturn(Observable.just(Response.success(musicBrainzArtistResponse)));
        given(wikipediaAPIService.get(anyString()))
                .willReturn(Observable.error(new ExternalServiceException(404)));


        TestSubscriber<ArtistDto> testSubscriber = new TestSubscriber<>();

        artistService.findByMusicBrainzId(UUID.randomUUID().toString()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        List<ArtistDto> onNextEvents = testSubscriber.getOnNextEvents();
        assertThat(onNextEvents, hasSize(1));
        ArtistDto artist = onNextEvents.get(0);

        assertThat(artist.getDescription(), is(nullValue()));
        assertThat(artist.getAlbums(), hasSize(25));
    }


}