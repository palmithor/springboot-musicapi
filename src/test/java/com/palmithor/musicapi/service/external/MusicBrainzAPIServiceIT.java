package com.palmithor.musicapi.service.external;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.service.model.MusicBrainzArtistResponse;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import retrofit2.Response;
import rx.Observable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Class only contains one test which should be ignored as it depends on the Music Brainz API which is not
 * good to include in the default test suite.
 * <p>
 * Test was mainly created to make sure the retrofit configuration was correct and that the service could actually
 * be consumed
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
public class MusicBrainzAPIServiceIT {

    // Class under test
    @Autowired private MusicBrainzAPIService service;

    @Test
    @Ignore
    public void testMusicBrainzRequest() throws Exception {
        Observable<Response<MusicBrainzArtistResponse>> observable = service.getByMBId("5b11f4ce-a62d-471e-81fc-a69a8278c7da");

        Response<MusicBrainzArtistResponse> first = observable
                .toBlocking()
                .first();

        assertThat(first.code(), is(200));
        assertThat(first.body(), is(notNullValue()));
    }
}