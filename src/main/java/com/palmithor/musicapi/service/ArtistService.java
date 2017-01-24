package com.palmithor.musicapi.service;

import com.palmithor.musicapi.dto.AlbumDTO;
import com.palmithor.musicapi.dto.ArtistDTO;
import com.palmithor.musicapi.service.external.CoverArtArchiveService;
import com.palmithor.musicapi.service.external.MusicBrainzService;
import com.palmithor.musicapi.service.external.WikipediaService;
import com.palmithor.musicapi.service.external.model.CoverArtArchiveResponse;
import com.palmithor.musicapi.service.external.model.MBArtistResponse;
import com.palmithor.musicapi.service.external.model.MBRelease;
import com.palmithor.musicapi.service.external.model.WikipediaResponse;
import com.palmithor.musicapi.service.util.MusicBrainzResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import retrofit2.Response;
import rx.Observable;
import rx.schedulers.Schedulers;

import java.util.ArrayList;
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

    private static final int API_SERVICE_TIMEOUT = 10; // TODO move to configurations


    @Autowired private MusicBrainzService musicBrainzService;
    @Autowired private WikipediaService wikipediaService;
    @Autowired private CoverArtArchiveService coverArtArchiveService;

    @Autowired private MusicBrainzResponseUtils musicBrainzResponseUtils;


    @SuppressWarnings("unchecked")
    public Observable<ArtistDTO> findByMusicBrainzId(final String mbid) {
        return musicBrainzService.getByMBId(mbid)
                .subscribeOn(Schedulers.computation())
                .flatMap((Response<MBArtistResponse> response) -> {
                    if (!response.isSuccessful()) {
                        throw createMusicBrainzError(response);
                    }
                    MBArtistResponse musicBrainzResponseBody = response.body();

                    Observable<Response<WikipediaResponse>> wikipediaRequestObservable = createWikipediaRequestObservable(musicBrainzResponseBody);
                    Observable<List<CoverArtArchiveResponseReleaseWrapper>> coverArtRequestsObservable = createCoverArtRequestObservable(musicBrainzResponseBody);

                    return Observable.zip(wikipediaRequestObservable, coverArtRequestsObservable, (wikipediaResponse, coverArtResponseWrapperList) -> {

                        ArtistDTO.ArtistDTOBuilder resultBuilder = ArtistDTO
                                .createBuilder()
                                .withMbid(musicBrainzResponseBody.getId());
                        if (wikipediaResponse.isSuccessful()) {
                            resultBuilder.withDescription(wikipediaResponse.body().getDescription());
                        }
                        List<AlbumDTO> albums = coverArtResponseWrapperList.stream()
                                .filter(i -> i.release != null && i.response != null && i.response.isSuccessful())
                                .map(i -> AlbumDTO.createBuilder().withId(i.release.getId()).withTitle(i.release.getTitle()).withImageUrl(i.response.body().getImageUrl()).build())
                                .collect(Collectors.toList());
                        resultBuilder.withAlbums(albums);
                        return resultBuilder.build();
                    });
                });
    }

    private Observable<List<CoverArtArchiveResponseReleaseWrapper>> createCoverArtRequestObservable(final MBArtistResponse musicBrainzResponseBody) {
        return Observable.from(musicBrainzResponseBody.getReleases())
                .flatMap(this::createCoverArtArchiveIdResponseObservable)
                .subscribeOn(Schedulers.newThread())
                .reduce(new ArrayList<CoverArtArchiveResponseReleaseWrapper>(), (list, item) -> {
                    // here list is a list with all the items that have added
                    // item is an instance of CoverArtArchiveIdResponseWrapper which contains the id of the release as well as the response
                    list.add(item);
                    return list;
                });
    }


    private Observable<Response<WikipediaResponse>> createWikipediaRequestObservable(final MBArtistResponse musicBrainzResponseBody) {
        Optional<String> wikipediaTitleOptional = musicBrainzResponseUtils.findWikipediaTitle(musicBrainzResponseBody);
        if (wikipediaTitleOptional.isPresent()) {
            return wikipediaService.get(wikipediaTitleOptional.get());
        } else {
            return Observable.just(Response.success(new WikipediaResponse()));
        }
    }

    private Observable<CoverArtArchiveResponseReleaseWrapper> createCoverArtArchiveIdResponseObservable(final MBRelease release) {
        return coverArtArchiveService.getByMBId(release.getId())
                .flatMap(response -> Observable.just(new CoverArtArchiveResponseReleaseWrapper(release, response)));
    }


    private ServiceException createMusicBrainzError(final Response<MBArtistResponse> artistResponse) {
        if (artistResponse.code() == 404) {
            return new ServiceException(ServiceError.MUSIC_BRAIN_ID_NOT_FOUND);
        }

        // todo map more response code

        return new ServiceException(ServiceError.INTERNAL_SERVER_ERROR);
    }


    /**
     * Helper class to let the release follow the observable
     */
    private class CoverArtArchiveResponseReleaseWrapper {
        private MBRelease release;
        private Response<CoverArtArchiveResponse> response;

        public CoverArtArchiveResponseReleaseWrapper(final MBRelease release, final Response<CoverArtArchiveResponse> response) {
            this.release = release;
            this.response = response;
        }
    }
}

/*
musicBrainzService.getByMBId(mbid)
                .flatMap((Response<MBArtistResponse> response) -> {
                    if (!response.isSuccessful()) {
                        throw createMusicBrainzError(response);
                    }
                    MBArtistResponse mbArtistResponse = response.body();
                    List<AlbumDTO> albums = musicBrainzResponseUtils.filterAlbums(mbArtistResponse);
                    Observable<Response<WikipediaResponse>> wikipediaObservable = getWikipediaObservable(mbArtistResponse);


                    // Finally zip all the observables, wikipedia, cover art and the already retrieved Music Brainz
                    ArtistDTO.ArtistDTOBuilder artistDtoBuilder = ArtistDTO.createBuilder()
                            .withMbid(mbArtistResponse.getId())
                            .withAlbums(albums);

                    return Observable.zip(Observable.just(artistDtoBuilder), wikipediaObservable, (artistDTOBuilder, wikipediaResponse) -> {
                        if (wikipediaResponse.isSuccessful()) {
                            artistDTOBuilder.withDescription(wikipediaResponse.body().getDescription());
                        }
                        return Observable.just(artistDTOBuilder);
                    });
                }).map(artistDTOBuilderObservable -> {
            return null;
        })

 */
