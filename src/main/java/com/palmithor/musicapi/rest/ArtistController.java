package com.palmithor.musicapi.rest;

import com.palmithor.musicapi.dto.ArtistDto;
import com.palmithor.musicapi.rest.response.ObjectResponse;
import com.palmithor.musicapi.rest.util.ArtistResponseHandler;
import com.palmithor.musicapi.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

/**
 * REST resource for artists
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@RestController
public class ArtistController {

    // This maps to a cache attribute in ehcache.xml
    private static final String cacheName = "artists";

    @Autowired private ArtistService artistService;
    @Autowired private ArtistResponseHandler responseMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/artists/{artistId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public DeferredResult<ResponseEntity<ObjectResponse<ArtistDto>>> getByMBID(@PathVariable String artistId) {
        return responseMapper.mapObjectResponse(cacheName, artistId, artistService.findByMusicBrainzId(artistId));
    }

}
