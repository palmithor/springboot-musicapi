package com.palmithor.musicapi.dto;

/**
 * Data transfer object with Album information
 *
 * @author palmithor
 * @since 24.1.2017.
 */
public class AlbumDTO {

    private final String title;
    private final String id;
    private final String imageUrl;

    public AlbumDTO(final String title, final String id, final String imageUrl) {
        this.title = title;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
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

        public AlbumDTO build() {
            return new AlbumDTO(title, id, imageUrl);
        }
    }
}
