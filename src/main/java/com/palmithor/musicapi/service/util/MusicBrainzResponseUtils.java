package com.palmithor.musicapi.service.util;

import com.palmithor.musicapi.dto.AlbumDTO;
import com.palmithor.musicapi.service.external.model.MBArtistRelation;
import com.palmithor.musicapi.service.external.model.MBArtistResponse;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Utility class for working with and parsing music brainz responses
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@Component
public class MusicBrainzResponseUtils {

    private static final String MB_WIKIPEDIA_RELATION_TYPE = "wikipedia";


    /**
     * Method searches through all relations of a Music Brainz response and if a relation with type
     * equal to wikipedia it verifies that the url is valid and finally returns the last part of it
     *
     * @param musicBrainzResponse the response retrieved from Music Brainz API
     * @return Optional which is present if and only if the wikipedia relation exists and that its url is valid
     */
    public Optional<String> findWikipediaTitle(final MBArtistResponse musicBrainzResponse) {
        Optional<String> result = Optional.empty();

        if (musicBrainzResponse != null && musicBrainzResponse.hasRelations()) {
            Optional<MBArtistRelation> wikipediaArtistRelationOptional = musicBrainzResponse.getRelations().stream().filter(artistRelation -> MB_WIKIPEDIA_RELATION_TYPE.equals(artistRelation.getType())).findFirst();
            if (wikipediaArtistRelationOptional.isPresent()) {
                MBArtistRelation artistRelation = wikipediaArtistRelationOptional.get();
                UrlValidator urlValidator = new UrlValidator();
                if (artistRelation.hasUrl() && urlValidator.isValid(artistRelation.getUrl().getUrl())) {
                    String[] splits = artistRelation.getUrl().getUrl().split("/");
                    if (splits.length > 0) {
                        // TODO add more validation that the url is structured as it should be
                        return Optional.of(splits[splits.length - 1]);
                    }
                }
            }
        }
        return result;
    }

    /**
     * Method iterates through the releases list of a Music Brainz response and returns a list which includes all
     * the releases as Album DTO with MB ID and title
     *
     * @param musicBrainzResponse the response retrieved from Music Brainz API
     * @return a list containing all albums in from the Music Brainz Response as AlbumDTO. An empty list if the response contains no albums.
     */
    public List<AlbumDTO> filterAlbums(final MBArtistResponse musicBrainzResponse) {
        final List<AlbumDTO> resultList = new ArrayList<>();
        if (musicBrainzResponse != null && musicBrainzResponse.hasReleases()) {
            List<AlbumDTO> albumList = musicBrainzResponse.getReleases().stream()
                    .map(release -> AlbumDTO.createBuilder()
                            .withId(release.getId())
                            .withTitle(release.getTitle())
                            .build())
                    .collect(Collectors.toList());
            resultList.addAll(albumList);
        }
        return resultList;
    }

}
