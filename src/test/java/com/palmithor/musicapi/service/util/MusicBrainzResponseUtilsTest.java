package com.palmithor.musicapi.service.util;

import com.palmithor.musicapi.dto.AlbumDTO;
import com.palmithor.musicapi.service.external.model.MBArtistRelation;
import com.palmithor.musicapi.service.external.model.MBArtistResponse;
import com.palmithor.musicapi.service.external.model.MBRelationUrl;
import com.palmithor.musicapi.service.external.model.MBRelease;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
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
        assertThat(utils.findWikipediaTitle(new MBArtistResponse()).isPresent(), is(false));
    }

    @Test
    public void testFindWikipediaTitleFailDueNoWikipediaType() throws Exception {
        MBArtistResponse response = MBArtistResponse
                .createBuilder()
                .withRelations(Collections.singletonList(
                        MBArtistRelation.createBuilder().withType("another_type").build()
                ))
                .build();
        assertThat(utils.findWikipediaTitle(response).isPresent(), is(false));
    }

    @Test
    public void testFindWikipediaTitleFailDueInvalidWikipediaRelation() throws Exception {
        MBArtistResponse response = MBArtistResponse
                .createBuilder()
                .withRelations(Collections.singletonList(
                        MBArtistRelation.createBuilder().withType("wikipedia").withUrl(MBRelationUrl.createBuilder().withUrl("invalid url").build()).build()
                ))
                .build();
        assertThat(utils.findWikipediaTitle(response).isPresent(), is(false));
    }

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void testFindWikipediaTitleOK() throws Exception {
        MBArtistResponse response = MBArtistResponse
                .createBuilder()
                .withRelations(Collections.singletonList(
                        MBArtistRelation.createBuilder().withType("wikipedia").withUrl(MBRelationUrl.createBuilder().withUrl("https://en.wikipedia.org/wiki/Nirvana_(band)").build()).build()
                ))
                .build();
        assertThat(utils.findWikipediaTitle(response).isPresent(), is(true));
        assertThat(utils.findWikipediaTitle(response).get(), is("Nirvana_(band)"));
    }

    @Test
    public void testFilterAlbumsWithNullObject() throws Exception {
        assertThat(utils.filterAlbums(null), hasSize(0));
    }

    @Test
    public void testFilterAlbumsWithEmptyObject() throws Exception {
        assertThat(utils.filterAlbums(new MBArtistResponse()), hasSize(0));
    }

    @Test
    public void testFilterAlbumsWithNoRelations() throws Exception {
        assertThat(utils.filterAlbums(MBArtistResponse.createBuilder().withRelations(new ArrayList<>()).build()), hasSize(0));
    }

    @Test
    public void testFilterAlbumsWithRelations() throws Exception {
        List<AlbumDTO> albums = utils.filterAlbums(MBArtistResponse
                .createBuilder()
                .withReleases(
                        Collections.singletonList(
                                MBRelease.createBuilder().withId("id").withTitle("title").build()
                        )).build());
        assertThat(albums, hasSize(1));
        assertThat(albums.get(0).getId(), is("id"));
        assertThat(albums.get(0).getTitle(), is("title"));
    }
}