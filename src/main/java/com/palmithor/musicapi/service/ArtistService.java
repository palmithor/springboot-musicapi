package com.palmithor.musicapi.service;

import com.palmithor.musicapi.dto.AlbumDto;
import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.service.external.CoverArtAPIService;
import com.palmithor.musicapi.service.external.MusicBrainzAPIService;
import com.palmithor.musicapi.service.external.WikipediaAPIService;
import com.palmithor.musicapi.service.model.MusicBrainzArtistResponse;
import com.palmithor.musicapi.service.model.WikipediaResponse;
import com.palmithor.musicapi.service.util.MusicBrainzResponseUtils;
import com.palmithor.musicapi.util.RetryWithDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for getting information about artists
 * <p>
 * Uses {@link WikipediaAPIService}, {@link MusicBrainzAPIService}
 * and {@link CoverArtAPIService} to fetch information about the artist
 * and its albums
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@Component
public class ArtistService {

    private static final Logger logger = LoggerFactory.getLogger(ArtistService.class);

    private static final String MB_PRIMARY_TYPE_ALBUM = "Album";


    @Autowired private MusicBrainzService musicBrainzService;
    @Autowired private WikipediaService wikipediaService;
    @Autowired private AlbumService albumService;
    @Autowired private MusicBrainzResponseUtils musicBrainzResponseUtils;


    public Observable<ArtistDto> findByMusicBrainzId(final String mbid) {
        return musicBrainzService.getById(mbid)
                .flatMap(response -> {
                    Observable<WikipediaResponse> wikipediaRequestObservable = createWikipediaRequestObservable(response);
                    Observable<List<AlbumDto>> albumsObservable = createCoverArtRequestObservable(response);

                    return Observable.zip(wikipediaRequestObservable, albumsObservable, (wikipediaResponse, albumDTOList) -> {

                        ArtistDto.Builder resultBuilder = ArtistDto
                                .createBuilder()
                                .withName(response.getName())
                                .withMBId(response.getId())
                                .withDescription(wikipediaResponse.getDescription());


                        resultBuilder.withAlbums(albumDTOList);
                        return resultBuilder.build();
                    });
                }).retryWhen(new RetryWithDelay(3, 3000)); // todo retry should be fore musicBrainzService;
    }

    private Observable<List<AlbumDto>> createCoverArtRequestObservable(final MusicBrainzArtistResponse musicBrainzResponseBody) {
        List<Observable<AlbumDto>> coverArtObservables = createCoverFetchObservables(musicBrainzResponseBody);
        if (coverArtObservables.isEmpty()) {
            return Observable.just(new ArrayList<>());
        } else {
            return Observable.combineLatest(coverArtObservables, albums ->
                    Arrays.stream(albums)
                            .filter(obj -> obj != null && obj instanceof AlbumDto)
                            .map(obj -> (AlbumDto) obj)
                            .collect(Collectors.toList()));
        }
    }


    private Observable<WikipediaResponse> createWikipediaRequestObservable(final MusicBrainzArtistResponse musicBrainzResponseBody) {
        Optional<String> wikipediaTitleOptional = musicBrainzResponseUtils.findWikipediaTitle(musicBrainzResponseBody);
        if (wikipediaTitleOptional.isPresent()) {
            return Observable.create(subscriber -> wikipediaService.getById(wikipediaTitleOptional.get())
                    .subscribe(subscriber::onNext, throwable -> {
                        logger.debug("An error occurred accessing wikipedia service");
                        subscriber.onNext(new WikipediaResponse());
                    }, subscriber::onCompleted));
        } else {
            return Observable.just(new WikipediaResponse());
        }
    }

    private List<Observable<AlbumDto>> createCoverFetchObservables(final MusicBrainzArtistResponse musicBrainzArtistResponse) {
        if (musicBrainzArtistResponse.hasReleases()) {
            return musicBrainzArtistResponse.getReleases().stream()
                    .filter(release -> MB_PRIMARY_TYPE_ALBUM.equals(release.getPrimaryType()))
                    .map(release -> albumService.fetchAlbumCoverByMBRelease(release))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

}