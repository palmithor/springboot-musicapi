package com.palmithor.musicapi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Data transfer object with Artist information
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class ArtistDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 8009221754239236264L;

    private final String mbid;
    private final String name;
    private final String description;
    private final List<AlbumDto> albums;


    public ArtistDto(final String mbid, final String name, final String description, final List<AlbumDto> albums) {
        this.name = name;
        this.mbid = mbid;
        this.description = description;
        this.albums = albums;
    }

    public String getName() {
        return name;
    }

    public String getMbid() {
        return mbid;
    }

    public String getDescription() {
        return description;
    }

    public List<AlbumDto> getAlbums() {
        return albums;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String mbid;
        private String name;
        private String description;
        private List<AlbumDto> albums;

        private Builder() {
        }


        public Builder withName(final String name) {
            this.name = name;
            return this;
        }

        public Builder withMBId(String mbid) {
            this.mbid = mbid;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public Builder withAlbums(List<AlbumDto> albums) {
            this.albums = albums;
            return this;
        }

        public ArtistDto build() {
            return new ArtistDto(mbid, name, description, albums);
        }
    }
}
