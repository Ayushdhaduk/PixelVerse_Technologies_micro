package com.example.demo.dto;

import com.example.demo.model.Artwork;
import java.util.List;

public class ArtworkDTO {

    private Long id;
    private String title;
    private String description;
    private Long artistId;
    private String imageUrl;
    private String thumbnailUrl;
    private Artwork.ResolutionType resolution;
    private Artwork.ArtworkCategory category;
    private String medium;
    private String fileFormat;
    private Double fileSizeMb;
    private Integer widthPx;
    private Integer heightPx;
    private Double price;
    private boolean forSale;
    private boolean active;
    private List<String> tags;
    private Long collectionId;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getThumbnailUrl() { return thumbnailUrl; }
    public void setThumbnailUrl(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }

    public Artwork.ResolutionType getResolution() { return resolution; }
    public void setResolution(Artwork.ResolutionType resolution) { this.resolution = resolution; }

    public Artwork.ArtworkCategory getCategory() { return category; }
    public void setCategory(Artwork.ArtworkCategory category) { this.category = category; }

    public String getMedium() { return medium; }
    public void setMedium(String medium) { this.medium = medium; }

    public String getFileFormat() { return fileFormat; }
    public void setFileFormat(String fileFormat) { this.fileFormat = fileFormat; }

    public Double getFileSizeMb() { return fileSizeMb; }
    public void setFileSizeMb(Double fileSizeMb) { this.fileSizeMb = fileSizeMb; }

    public Integer getWidthPx() { return widthPx; }
    public void setWidthPx(Integer widthPx) { this.widthPx = widthPx; }

    public Integer getHeightPx() { return heightPx; }
    public void setHeightPx(Integer heightPx) { this.heightPx = heightPx; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public boolean isForSale() { return forSale; }
    public void setForSale(boolean forSale) { this.forSale = forSale; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public Long getCollectionId() { return collectionId; }
    public void setCollectionId(Long collectionId) { this.collectionId = collectionId; }
}
