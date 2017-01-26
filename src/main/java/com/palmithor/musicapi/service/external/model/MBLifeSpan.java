package com.palmithor.musicapi.service.external.model;

/**
 * Model for Music Brainz life span
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class MBLifeSpan {

    private final String end;
    private final String begin;
    private final Boolean ended;

    public MBLifeSpan() {
        this.end = null;
        this.begin = null;
        this.ended = null;
    }

    public MBLifeSpan(final String end, final String begin, final Boolean ended) {
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
