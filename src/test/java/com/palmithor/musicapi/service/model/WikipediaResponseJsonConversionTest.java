package com.palmithor.musicapi.service.model;

import com.palmithor.musicapi.JsonTestUtils;
import com.palmithor.musicapi.TestConstants;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Test JSON conversion of Wikipedia response
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class WikipediaResponseJsonConversionTest {


    @Test
    public void testConvertWikipediaResponse() throws FileNotFoundException {
        WikipediaResponse wikipediaResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.WIKIPEDIA_RESPONSE, WikipediaResponse.class);
        assertThat(wikipediaResponse.getBatchComplete(), is(isEmptyString()));
        assertThat(wikipediaResponse.getQuery().getPages().size(), is(1));
        WikipediaPage wikipediaPage = wikipediaResponse.getQuery().getPages().values().iterator().next();
        assertThat(wikipediaPage.getDescription(), is(not(isEmptyOrNullString())));
        assertThat(wikipediaPage.getTitle(), is("Nirvana (band)"));
        assertThat(wikipediaPage.getNs(), is(0));
        assertThat(wikipediaPage.getPageId(), is(21231));
    }
}