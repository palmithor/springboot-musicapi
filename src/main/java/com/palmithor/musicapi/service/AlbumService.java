package com.palmithor.musicapi.service;

import com.palmithor.musicapi.dto.AlbumDto;
import com.palmithor.musicapi.service.external.CoverArtArchiveService;
import com.palmithor.musicapi.service.external.model.MBRelease;
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

    @Autowired CoverArtArchiveService coverArtArchiveService;

    Observable<AlbumDto> fetchAlbumCoverByMBRelease(final MBRelease release) {
        return Observable.create(subscriber -> {
            final AlbumDto.Builder albumBuilder = AlbumDto.createBuilder().withId(release.getId()).withTitle(release.getTitle());
            coverArtArchiveService.getByMBId(release.getId())
                    .subscribeOn(Schedulers.io())
                    .subscribe(coverArtArchiveResponse -> {
                                if (coverArtArchiveResponse.isSuccessful()) {
                                    albumBuilder.withImageUrl(coverArtArchiveResponse.body().getImageUrl());
                                }
                                subscriber.onNext(albumBuilder.build());

                            }, throwable -> {
                                subscriber.onNext(albumBuilder.build());
                                subscriber.onCompleted();
                            }
                            , subscriber::onCompleted);
        });
    }
}
