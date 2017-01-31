package com.palmithor.musicapi.rest.artist;

import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.rest.ResponseHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Response handler for ArtistDTO responses
 *
 * @author palmithor
 * @since 25.1.2017.
 */
@Component
public class ArtistResponseHandler extends ResponseHandler<ArtistDto> {

    public ArtistResponseHandler() {
        super(ArtistDto.class);
    }

    @Override
    protected boolean shouldCache(final ArtistDto obj) {
        return !StringUtils.isEmpty(obj.getDescription()) &&
                obj.getAlbums() != null &&
                !obj.getAlbums().isEmpty();
    }
}
