package com.palmithor.musicapi.service;

import com.palmithor.musicapi.dto.AlbumDto;
import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.service.external.MusicBrainzService;
import com.palmithor.musicapi.service.external.WikipediaService;
import com.palmithor.musicapi.service.external.model.MBArtistResponse;
import com.palmithor.musicapi.service.external.model.WikipediaResponse;
import com.palmithor.musicapi.service.util.MusicBrainzResponseUtils;
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

    private static final String MB_PRIMARY_TYPE_ALBUM = "Album";


    @Autowired private MusicBrainzService musicBrainzService;
    @Autowired private WikipediaService wikipediaService;
    @Autowired private AlbumService albumService;
    @Autowired private MusicBrainzResponseUtils musicBrainzResponseUtils;


    public Observable<ArtistDto> findByMusicBrainzId(final String mbid) {
        return musicBrainzService.getByMBId(mbid)
                .flatMap((Response<MBArtistResponse> response) -> {
                    if (!response.isSuccessful()) {
                        throw createMusicBrainzError(response);
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
                        }

                        resultBuilder.withAlbums(albumDTOList);
                        return resultBuilder.build();
                    });
                });
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


    private ServiceException createMusicBrainzError(final Response<MBArtistResponse> artistResponse) {
        if (artistResponse.code() == 404) {
            return new ServiceException(ServiceError.MUSIC_BRAIN_ID_NOT_FOUND);
        }

        // todo map more response code

        return new ServiceException(ServiceError.INTERNAL_SERVER_ERROR);
    }
}