package com.palmithor.musicapi.dto;

import java.util.List;

/**
 * Data transfer object with Artist information
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class ArtistDTO {

    private final String mbid;
    private final String description;
    private final List<AlbumDTO> albums;


    public ArtistDTO(final String mbid, final String description, final List<AlbumDTO> albums) {
        this.mbid = mbid;
        this.description = description;
        this.albums = albums;
    }

    public static ArtistDTOBuilder createBuilder() {
        return new ArtistDTOBuilder();
    }

    public String getMbid() {
        return mbid;
    }

    public static final class ArtistDTOBuilder {
        private String mbid;
        private String description;
        private List<AlbumDTO> albums;

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

        public ArtistDTOBuilder withAlbums(List<AlbumDTO> albums) {
            this.albums = albums;
            return this;
        }

        public ArtistDTO build() {
            return new ArtistDTO(mbid, description, albums);
        }
    }
}
