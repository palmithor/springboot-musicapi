package com.palmithor.musicapi.service.external;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.service.external.model.CoverArtArchiveResponse;
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
 * Class only contains one test which should be ignored as it depends on the Cover Art Archive API which is not
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
public class CoverArtArchiveServiceIT {

    // Class under test
    @Autowired private CoverArtArchiveService service;

    @Test
    public void testCoverArtArchiveRequest() throws Exception {
        Observable<Response<CoverArtArchiveResponse>> observable = service.getByMBId("1b022e01-4da6-387b-8658-8678046e4cef");

        Response<CoverArtArchiveResponse> first = observable
                .toBlocking()
                .first();

        assertThat(first.code(), is(200));
        assertThat(first.body(), is(notNullValue()));
    }
}