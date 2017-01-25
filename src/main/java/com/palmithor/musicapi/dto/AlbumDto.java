package com.palmithor.musicapi.dto;

/**
 * Data transfer object with Album information
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class AlbumDto {

    private final String title;
    private final String id;
    private final String coverImageUrl;

    public AlbumDto(final String title, final String id, final String coverImageUrl) {
        this.title = title;
        this.id = id;
        this.coverImageUrl = coverImageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public static Builder createBuilder() {
        return new Builder();
    }

    public static final class Builder {
        private String title;
        private String id;
        private String imageUrl;

        private Builder() {
        }

        public Builder withTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
            return this;
        }

        public AlbumDto build() {
            return new AlbumDto(title, id, imageUrl);
        }
    }
}
