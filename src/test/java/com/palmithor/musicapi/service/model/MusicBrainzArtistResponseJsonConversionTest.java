package com.palmithor.musicapi.service.model;

import com.palmithor.musicapi.JsonTestUtils;
import com.palmithor.musicapi.TestConstants;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * Test JSON conversion of MusicBrainz response
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class MusicBrainzArtistResponseJsonConversionTest {


    @Test
    public void testConvertMBArtistResponse() throws FileNotFoundException {
        MusicBrainzArtistResponse artist = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.MUSIC_BRAINZ_ARTIST_RESPONSE, MusicBrainzArtistResponse.class);
        assertThat(artist.getName(), is("Nirvana"));
        assertThat(artist.getSortName(), is("Nirvana"));
        assertThat(artist.getDisambiguation(), is("90s US grunge band"));
        assertThat(artist.getCountry(), is("US"));
        assertThat(artist.getTypeId(), is("e431f5f6-b5d2-343d-8b36-72607fffb74b"));
        assertThat(artist.getType(), is("Group"));
        assertThat(artist.getIsnis(), hasSize(1));
        assertThat(artist.getRelations(), hasSize(38));
        assertThat(artist.getReleases(), hasSize(25));
        assertThat(artist.getLifeSpan().getEnd(), is("1994-04-05"));
        assertThat(artist.getLifeSpan().getBegin(), is("1988-01"));
        assertThat(artist.getLifeSpan().hasEnded(), is(true));
    }
}