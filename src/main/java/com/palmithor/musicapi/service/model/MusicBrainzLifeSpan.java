package com.palmithor.musicapi.service.model;

import java.io.Serializable;

/**
 * Model for Music Brainz life span
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class MusicBrainzLifeSpan implements Serializable {


    private static final long serialVersionUID = -3951081321687947217L;
    private final String end;
    private final String begin;
    private final Boolean ended;

    public MusicBrainzLifeSpan() {
        this.end = null;
        this.begin = null;
        this.ended = null;
    }

    public MusicBrainzLifeSpan(final String end, final String begin, final Boolean ended) {
        this.end = end;
        this.begin = begin;
        this.ended = ended;
    }

    public String getEnd() {
        return end;
    }

    public String getBegin() {
        return begin;
    }

    public Boolean hasEnded() {
        return ended;
    }
}
