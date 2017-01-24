package com.palmithor.musicapi.service.model;

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
public class WikipediaResponseJsonConversionTest extends JsonConversionTest<WikipediaParent> {


    @Test
    public void testConvertWikipediaResponse() throws FileNotFoundException {
        WikipediaParent wikipediaParent = readJsonFromFile("service/WikipediaResponse.json", WikipediaParent.class);
        assertThat(wikipediaParent.getBatchComplete(), is(isEmptyString()));
        assertThat(wikipediaParent.getQuery().getPages().size(), is(1));
        WikipediaPage wikipediaPage = wikipediaParent.getQuery().getPages().values().iterator().next();
        assertThat(wikipediaPage.getDescription(), is(not(isEmptyOrNullString())));
        assertThat(wikipediaPage.getTitle(), is("Nirvana (band)"));
        assertThat(wikipediaPage.getNs(), is(0));
        assertThat(wikipediaPage.getPageId(), is(21231));
    }
}