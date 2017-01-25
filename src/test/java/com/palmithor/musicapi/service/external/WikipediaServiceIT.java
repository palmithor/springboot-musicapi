package com.palmithor.musicapi.service.external;

import com.palmithor.musicapi.App;
import com.palmithor.musicapi.service.external.model.WikipediaResponse;
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
 * Class only contains one test which should be ignored as it depends on the Wikipedia API which is not
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
public class WikipediaServiceIT {

    // Class under test
    @Autowired private WikipediaService service;

    @Test
    @Ignore
    public void testWikipediaRequest() throws Exception {
        Observable<Response<WikipediaResponse>> observable = service.get("Nirvana_(band)");

        Response<WikipediaResponse> first = observable
                .toBlocking()
                .first();

        assertThat(first.code(), is(200));
        assertThat(first.body(), is(notNullValue()));
    }
}