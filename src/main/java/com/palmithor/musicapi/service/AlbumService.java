package com.palmithor.musicapi.service;

import com.palmithor.musicapi.dto.AlbumDto;
import com.palmithor.musicapi.service.model.MusicBrainzRelease;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rx.Observable;
import rx.schedulers.Schedulers;


/**
 * Wrapping of Cover Art Archive Service for handling data
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@Component
public class AlbumService {

    private static final Logger logger = LoggerFactory.getLogger(AlbumService.class);

    @Autowired private CoverArtService coverArtService;

    Observable<AlbumDto> fetchAlbumCoverByMBRelease(final MusicBrainzRelease release) {
        return Observable.create(subscriber -> {
            final AlbumDto.Builder albumBuilder = AlbumDto.createBuilder().withId(release.getId()).withTitle(release.getTitle());
            coverArtService.getById(release.getId())
                    .subscribeOn(Schedulers.io())
                    .subscribe(response -> {
                                albumBuilder.withImageUrl(response.getImageUrl());
                                subscriber.onNext(albumBuilder.build());

                            }, throwable -> {
                                logger.debug("An error occurred fetching album covers", throwable);
                                subscriber.onNext(albumBuilder.build());
                                subscriber.onCompleted();
                            }
                            , subscriber::onCompleted);
        });
    }
}
