package com.palmithor.musicapi.dto;

import java.util.List;

/**
 * Data transfer object with Artist information
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class ArtistDto {

    private final String mbid;
    private final String description;
    private final List<AlbumDto> albums;


    public ArtistDto(final String mbid, final String description, final List<AlbumDto> albums) {
        this.mbid = mbid;
        this.description = description;
        this.albums = albums;
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

    public static ArtistDTOBuilder createBuilder() {
        return new ArtistDTOBuilder();
    }

    public static final class ArtistDTOBuilder {
        private String mbid;
        private String description;
        private List<AlbumDto> albums;

        private ArtistDTOBuilder() {
        }


        public ArtistDTOBuilder withMbid(String mbid) {
            this.mbid = mbid;
            return this;
        }

        public ArtistDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public ArtistDTOBuilder withAlbums(List<AlbumDto> albums) {
            this.albums = albums;
            return this;
        }

        public ArtistDto build() {
            return new ArtistDto(mbid, description, albums);
        }
    }
}
