package com.palmithor.musicapi.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST resource for artists
 *
 * @author palmithor
 * @since 24.1.2017.
 */
@RestController
public class ArtistController {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}
