package com.palmithor.musicapi.service.model;

import com.palmithor.musicapi.JsonTestUtils;
import com.palmithor.musicapi.TestConstants;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test JSON conversion of Cover Art Archive response
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class CoverArtResponseJsonConversionTest {


    @Test
    public void testCoverArtArchiveResponse() throws FileNotFoundException {
        CoverArtResponse coverArtResponse = JsonTestUtils.readJsonFromFile(TestConstants.JsonFilePaths.COVER_ART_ARCHIVE_RESPONSE, CoverArtResponse.class);

        assertThat(coverArtResponse.getReleaseUrl(), is("http://musicbrainz.org/release/a146429a-cedc-3ab0-9e41-1aaf5f6cdc2d"));
        assertThat(coverArtResponse.getImages(), hasSize(1));
        CoverArtArchiveImage coverArtArchiveImage = coverArtResponse.getImages().get(0);
        assertThat(coverArtArchiveImage.getId(), is("3012495605"));
        assertThat(coverArtArchiveImage.getEdit(), is(20473306L));
        assertThat(coverArtArchiveImage.getUrl(), is("http://coverartarchive.org/release/a146429a-cedc-3ab0-9e41-1aaf5f6cdc2d/3012495605.jpg"));
        assertThat(coverArtArchiveImage.getTypes(), hasSize(1));
        assertThat(coverArtArchiveImage.getTypes(), contains("Front"));
        assertThat(coverArtArchiveImage.getThumbnails().getLargeImageUrl(), is("http://coverartarchive.org/release/a146429a-cedc-3ab0-9e41-1aaf5f6cdc2d/3012495605-500.jpg"));
        assertThat(coverArtArchiveImage.getThumbnails().getSmallImageUrl(), is("http://coverartarchive.org/release/a146429a-cedc-3ab0-9e41-1aaf5f6cdc2d/3012495605-250.jpg"));
        assertTrue(coverArtArchiveImage.getApproved());
        assertTrue(coverArtArchiveImage.getFront());
        assertFalse(coverArtArchiveImage.getBack());
    }
}