package com.palmithor.musicapi.service;

import com.palmithor.musicapi.dto.AlbumDto;
import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.service.external.MusicBrainzService;
import com.palmithor.musicapi.service.external.WikipediaService;
import com.palmithor.musicapi.service.external.model.MBArtistResponse;
import com.palmithor.musicapi.service.external.model.WikipediaResponse;
import com.palmithor.musicapi.service.util.MusicBrainzResponseUtils;
import com.palmithor.musicapi.util.RetryWithDelay;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import rx.Observable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service for getting information about artists
 * <p>
 * Uses {@link com.palmithor.musicapi.service.external.WikipediaService}, {@link com.palmithor.musicapi.service.external.MusicBrainzService}
 * and {@link com.palmithor.musicapi.service.external.CoverArtArchiveService} to fetch information about the artist
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
        return musicBrainzService.getByMBId(mbid)
                .flatMap((Response<MBArtistResponse> response) -> {
                    if (!response.isSuccessful()) {
                        throw mapMBErrorToServiceException(response);
                    }
                    MBArtistResponse musicBrainzResponseBody = response.body();

                    Observable<Response<WikipediaResponse>> wikipediaRequestObservable = createWikipediaRequestObservable(musicBrainzResponseBody);
                    Observable<List<AlbumDto>> albumsObservable = createCoverArtRequestObservable(musicBrainzResponseBody);

                    return Observable.zip(wikipediaRequestObservable, albumsObservable, (wikipediaResponse, albumDTOList) -> {

                        ArtistDto.Builder resultBuilder = ArtistDto
                                .createBuilder()
                                .withName(musicBrainzResponseBody.getName())
                                .withMBId(musicBrainzResponseBody.getId());
                        if (wikipediaResponse.isSuccessful()) {
                            resultBuilder.withDescription(wikipediaResponse.body().getDescription());
                        } else {
                            logger.info("Unable to get Wikipedia info. Status code: ", wikipediaResponse.code());
                        }

                        resultBuilder.withAlbums(albumDTOList);
                        return resultBuilder.build();
                    });
                }).retryWhen(new RetryWithDelay(3, 5000)); // todo retry should be fore musicBrainzService;
    }

    private Observable<List<AlbumDto>> createCoverArtRequestObservable(final MBArtistResponse musicBrainzResponseBody) {
        List<Observable<AlbumDto>> coverFetchObservables = createCoverFetchObservables(musicBrainzResponseBody);
        if (coverFetchObservables.isEmpty()) {
            return Observable.just(new ArrayList<>());
        } else {
            return Observable.combineLatest(coverFetchObservables, albums ->
                    Arrays.stream(albums)
                            .filter(obj -> obj != null && obj instanceof AlbumDto)
                            .map(obj -> (AlbumDto) obj)
                            .collect(Collectors.toList()));
        }
    }


    private Observable<Response<WikipediaResponse>> createWikipediaRequestObservable(final MBArtistResponse musicBrainzResponseBody) {
        Optional<String> wikipediaTitleOptional = musicBrainzResponseUtils.findWikipediaTitle(musicBrainzResponseBody);
        if (wikipediaTitleOptional.isPresent()) {
            return wikipediaService.get(wikipediaTitleOptional.get());
        } else {
            return Observable.just(Response.success(new WikipediaResponse()));
        }
    }

    private List<Observable<AlbumDto>> createCoverFetchObservables(final MBArtistResponse mbArtistResponse) {
        if (mbArtistResponse.hasReleases()) {
            return mbArtistResponse.getReleases().stream()
                    .filter(release -> MB_PRIMARY_TYPE_ALBUM.equals(release.getPrimaryType()))
                    .map(release -> albumService.fetchAlbumCoverByMBRelease(release))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }


    private ServiceException mapMBErrorToServiceException(final Response<MBArtistResponse> artistResponse) {
        switch (artistResponse.code()) {
            case 404:
                return new ServiceException(ServiceError.MUSIC_BRAINZ_ID_NOT_FOUND);
            case 400:
                return new ServiceException(ServiceError.MUSIC_BRAINZ_ID_INVALID);
            case 500:
                return new ServiceException(ServiceError.MUSIC_BRAINZ_INACCESSIBLE);
            default:
                return new ServiceException(ServiceError.INTERNAL_SERVER_ERROR);
        }
    }
}