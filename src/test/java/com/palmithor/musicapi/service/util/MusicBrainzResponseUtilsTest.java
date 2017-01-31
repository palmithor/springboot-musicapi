package com.palmithor.musicapi.service.util;

import com.palmithor.musicapi.service.model.MusicBrainzArtistRelation;
import com.palmithor.musicapi.service.model.MusicBrainzArtistResponse;
import com.palmithor.musicapi.service.model.MusicBrainzRelationUrl;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author palmithor
 * @since 24.1.2017.
 */
public class MusicBrainzResponseUtilsTest {


    private MusicBrainzResponseUtils utils;

    @Before
    public void setUp() throws Exception {
        this.utils = new MusicBrainzResponseUtils();
    }

    @Test
    public void testFindWikipediaTitleFailDueNullObject() throws Exception {
        assertThat(utils.findWikipediaTitle(null).isPresent(), is(false));
    }

    @Test
    public void testFindWikipediaTitleFailDuEmptyObject() throws Exception {
        assertThat(utils.findWikipediaTitle(new MusicBrainzArtistResponse()).isPresent(), is(false));
    }

    @Test
    public void testFindWikipediaTitleFailDueNoWikipediaType() throws Exception {
        MusicBrainzArtistResponse response = MusicBrainzArtistResponse
                .createBuilder()
                .withRelations(Collections.singletonList(
                        MusicBrainzArtistRelation.createBuilder().withType("another_type").build()
                ))
                .build();
        assertThat(utils.findWikipediaTitle(response).isPresent(), is(false));
    }

    @Test
    public void testFindWikipediaTitleFailDueInvalidWikipediaRelation() throws Exception {
        MusicBrainzArtistResponse response = MusicBrainzArtistResponse
                .createBuilder()
                .withRelations(Collections.singletonList(
                        MusicBrainzArtistRelation.createBuilder().withType("wikipedia").withUrl(MusicBrainzRelationUrl.createBuilder().withUrl("invalid url").build()).build()
                ))
                .build();
        assertThat(utils.findWikipediaTitle(response).isPresent(), is(false));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void testFindWikipediaTitleOK() throws Exception {
        MusicBrainzArtistResponse response = MusicBrainzArtistResponse
                .createBuilder()
                .withRelations(Collections.singletonList(
                        MusicBrainzArtistRelation.createBuilder().withType("wikipedia").withUrl(MusicBrainzRelationUrl.createBuilder().withUrl("https://en.wikipedia.org/wiki/Nirvana_(band)").build()).build()
                ))
                .build();
        assertThat(utils.findWikipediaTitle(response).isPresent(), is(true));
        assertThat(utils.findWikipediaTitle(response).get(), is("Nirvana_(band)"));
    }

}