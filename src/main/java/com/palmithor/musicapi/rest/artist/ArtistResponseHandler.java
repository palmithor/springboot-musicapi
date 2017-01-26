package com.palmithor.musicapi.rest.artist;

import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.rest.ResponseHandler;
import org.springframework.stereotype.Component;

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
}
