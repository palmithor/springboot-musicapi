package com.palmithor.musicapi.service;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.JsonTestUtils;
import com.palmithor.musicapi.TestConstants;
import com.palmithor.musicapi.dto.AlbumDto;
import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.service.external.MusicBrainzService;
import com.palmithor.musicapi.service.external.WikipediaService;
import com.palmithor.musicapi.service.external.model.MBArtistResponse;
import com.palmithor.musicapi.service.external.model.WikipediaResponse;
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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
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


    @MockBean private MusicBrainzService musicBrainzService;
    @MockBean private AlbumService albumService;
    @MockBean private WikipediaService wikipediaService;

    @Autowired private ArtistService artistService;

    @Test
    public void testFindByMusicBrainzIdSuccessful() throws FileNotFoundException {
        MBArtistResponse mbArtistResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.MUSIC_BRAINZ_ARTIST_RESPONSE, MBArtistResponse.class);
        WikipediaResponse wikipediaResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.WIKIPEDIA_RESPONSE, WikipediaResponse.class);

        given(musicBrainzService.getByMBId(anyString()))
                .willReturn(Observable.just(Response.success(mbArtistResponse)));
        given(wikipediaService.get(anyString()))
                .willReturn(Observable.just(Response.success(wikipediaResponse)));
        given(albumService.fetchAlbumCoverByMBRelease(any()))
                .willReturn(
                        Observable.just(AlbumDto.createBuilder()
                                .withId(UUID.randomUUID().toString())
                                .withImageUrl("http://url.com/image.jpg")
                                .withTitle("Album")
                                .build())
                );

        TestSubscriber<ArtistDto> testSubscriber = new TestSubscriber<>();

        artistService.findByMusicBrainzId(UUID.randomUUID().toString()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertCompleted();
        List<ArtistDto> onNextEvents = testSubscriber.getOnNextEvents();
        assertThat(onNextEvents, hasSize(1));
        ArtistDto artist = onNextEvents.get(0);

        assertThat(artist.getMbid(), is(mbArtistResponse.getId()));
        assertThat(artist.getDescription(), is(wikipediaResponse.getDescription()));
        assertThat(artist.getAlbums(), hasSize(25));
    }

    @Test
    public void testFindByMusicBrainzIdNotFound() throws FileNotFoundException {

        given(musicBrainzService.getByMBId(anyString()))
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

        MBArtistResponse mockResponse = MBArtistResponse.createBuilder()
                .withId(UUID.randomUUID().toString()).withName("Band Name").build();
        given(musicBrainzService.getByMBId(anyString()))
                .willReturn(Observable.just(Response.success(mockResponse)));

        TestSubscriber<ArtistDto> testSubscriber = new TestSubscriber<>();

        artistService.findByMusicBrainzId(UUID.randomUUID().toString()).subscribe(testSubscriber);
        testSubscriber.awaitTerminalEvent();

        List<ArtistDto> onNextEvents = testSubscriber.getOnNextEvents();
        assertThat(onNextEvents, hasSize(1));
        ArtistDto artist = onNextEvents.get(0);

        assertThat(artist.getDescription(), is(nullValue()));
        assertThat(artist.getMbid(), is(mockResponse.getId()));
        assertThat(artist.getAlbums(), hasSize(0));
    }


}