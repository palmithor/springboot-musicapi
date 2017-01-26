package com.palmithor.musicapi.service;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.JsonTestUtils;
import com.palmithor.musicapi.TestConstants;
import com.palmithor.musicapi.dto.AlbumDto;
import com.palmithor.musicapi.service.external.CoverArtArchiveService;
import com.palmithor.musicapi.service.external.model.CoverArtArchiveResponse;
import com.palmithor.musicapi.service.external.model.MBRelease;
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

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 * Unit test for AlbumService
 *
 * @author palmithor
 * @since 25.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class AlbumServiceTest {

    @MockBean private CoverArtArchiveService coverArtArchiveService;

    @Autowired private AlbumService albumService;

    @Test
    public void testGetAlbumDTOByMBReleaseSuccess() throws Exception {
        CoverArtArchiveResponse coverArtArchiveResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.COVER_ART_ARCHIVE_RESPONSE, CoverArtArchiveResponse.class);
        MBRelease release = getMBReleaseObj();

        given(coverArtArchiveService.getByMBId(release.getId()))
                .willReturn(Observable.just(Response.success(coverArtArchiveResponse)));
        AlbumDto album = albumService.fetchAlbumCoverByMBRelease(release)
                .toBlocking()
                .first();

        assertThat(album.getId(), is(release.getId()));
        assertThat(album.getTitle(), is(release.getTitle()));
        assertThat(album.getImage(), is(coverArtArchiveResponse.getImageUrl()));
    }

    @Test
    public void testGetAlbumDTOByMBReleaseFailedCoverRequest() throws Exception {
        MBRelease release = getMBReleaseObj();

        given(coverArtArchiveService.getByMBId(release.getId()))
                .willReturn(Observable.just(Response.error(404,
                        ResponseBody.create(MediaType.parse("application/json"), ""))));
        AlbumDto album = albumService.fetchAlbumCoverByMBRelease(release)
                .toBlocking()
                .first();

        assertThat(album.getId(), is(release.getId()));
        assertThat(album.getTitle(), is(release.getTitle()));
        assertThat(album.getImage(), is(nullValue()));
    }

    @Test
    public void testGetAlbumDTOByMBReleaseErrorCoverRequest() throws Exception {
        MBRelease release = getMBReleaseObj();

        given(coverArtArchiveService.getByMBId(release.getId()))
                .willReturn(Observable.error(new Exception()));
        AlbumDto album = albumService.fetchAlbumCoverByMBRelease(release)
                .toBlocking()
                .first();

        assertThat(album.getId(), is(release.getId()));
        assertThat(album.getTitle(), is(release.getTitle()));
        assertThat(album.getImage(), is(nullValue()));
    }

    private MBRelease getMBReleaseObj() {
        String title = "title";
        String id = UUID.randomUUID().toString();
        return MBRelease.createBuilder()
                .withId(id)
                .withTitle(title)
                .build();
    }
}