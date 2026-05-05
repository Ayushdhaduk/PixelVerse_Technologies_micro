package com.example.demo.service;

import com.example.demo.dto.ArtworkDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Artwork;
import com.example.demo.model.Collection;
import com.example.demo.repository.ArtworkRepository;
import com.example.demo.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtworkService {

    @Autowired
    private ArtworkRepository artworkRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    // Create Artwork
    public ArtworkDTO createArtwork(ArtworkDTO dto) {
        Artwork artwork = mapToEntity(dto);
        if (dto.getCollectionId() != null) {
            Collection collection = collectionRepository.findById(dto.getCollectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Collection not found with id: " + dto.getCollectionId()));
            artwork.setCollection(collection);
        }
        Artwork saved = artworkRepository.save(artwork);
        return mapToDTO(saved);
    }

    // Get All Artworks
    public List<ArtworkDTO> getAllArtworks() {
        return artworkRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get by ID
    public ArtworkDTO getArtworkById(Long id) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork not found with id: " + id));
        return mapToDTO(artwork);
    }

    // Get by Artist
    public List<ArtworkDTO> getArtworksByArtist(Long artistId) {
        return artworkRepository.findByArtistId(artistId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get by Category
    public List<ArtworkDTO> getArtworksByCategory(Artwork.ArtworkCategory category) {
        return artworkRepository.findByCategory(category).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get by Resolution
    public List<ArtworkDTO> getArtworksByResolution(Artwork.ResolutionType resolution) {
        return artworkRepository.findByResolution(resolution).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get For Sale
    public List<ArtworkDTO> getArtworksForSale() {
        return artworkRepository.findByForSaleTrue().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get by Collection
    public List<ArtworkDTO> getArtworksByCollection(Long collectionId) {
        return artworkRepository.findByCollectionId(collectionId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Search by Title
    public List<ArtworkDTO> searchByTitle(String keyword) {
        return artworkRepository.findByTitleContainingIgnoreCase(keyword).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Update Artwork
    public ArtworkDTO updateArtwork(Long id, ArtworkDTO dto) {
        Artwork artwork = artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork not found with id: " + id));

        if (dto.getTitle() != null) artwork.setTitle(dto.getTitle());
        if (dto.getDescription() != null) artwork.setDescription(dto.getDescription());
        if (dto.getImageUrl() != null) artwork.setImageUrl(dto.getImageUrl());
        if (dto.getThumbnailUrl() != null) artwork.setThumbnailUrl(dto.getThumbnailUrl());
        if (dto.getResolution() != null) artwork.setResolution(dto.getResolution());
        if (dto.getCategory() != null) artwork.setCategory(dto.getCategory());
        if (dto.getMedium() != null) artwork.setMedium(dto.getMedium());
        if (dto.getFileFormat() != null) artwork.setFileFormat(dto.getFileFormat());
        if (dto.getFileSizeMb() != null) artwork.setFileSizeMb(dto.getFileSizeMb());
        if (dto.getWidthPx() != null) artwork.setWidthPx(dto.getWidthPx());
        if (dto.getHeightPx() != null) artwork.setHeightPx(dto.getHeightPx());
        if (dto.getPrice() != null) artwork.setPrice(dto.getPrice());
        if (dto.getTags() != null) artwork.setTags(dto.getTags());
        artwork.setForSale(dto.isForSale());

        if (dto.getCollectionId() != null) {
            Collection collection = collectionRepository.findById(dto.getCollectionId())
                    .orElseThrow(() -> new ResourceNotFoundException("Collection not found with id: " + dto.getCollectionId()));
            artwork.setCollection(collection);
        }

        return mapToDTO(artworkRepository.save(artwork));
    }

    // Delete Artwork
    public String deleteArtwork(Long id) {
        artworkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artwork not found with id: " + id));
        artworkRepository.deleteById(id);
        return "Artwork with id " + id + " deleted successfully.";
    }

    // --- Mapping ---
    private Artwork mapToEntity(ArtworkDTO dto) {
        Artwork artwork = new Artwork();
        artwork.setTitle(dto.getTitle());
        artwork.setDescription(dto.getDescription());
        artwork.setArtistId(dto.getArtistId());
        artwork.setImageUrl(dto.getImageUrl());
        artwork.setThumbnailUrl(dto.getThumbnailUrl());
        artwork.setResolution(dto.getResolution());
        artwork.setCategory(dto.getCategory());
        artwork.setMedium(dto.getMedium());
        artwork.setFileFormat(dto.getFileFormat());
        artwork.setFileSizeMb(dto.getFileSizeMb());
        artwork.setWidthPx(dto.getWidthPx());
        artwork.setHeightPx(dto.getHeightPx());
        artwork.setPrice(dto.getPrice());
        artwork.setForSale(dto.isForSale());
        artwork.setActive(dto.isActive());
        artwork.setTags(dto.getTags());
        return artwork;
    }

    private ArtworkDTO mapToDTO(Artwork artwork) {
        ArtworkDTO dto = new ArtworkDTO();
        dto.setId(artwork.getId());
        dto.setTitle(artwork.getTitle());
        dto.setDescription(artwork.getDescription());
        dto.setArtistId(artwork.getArtistId());
        dto.setImageUrl(artwork.getImageUrl());
        dto.setThumbnailUrl(artwork.getThumbnailUrl());
        dto.setResolution(artwork.getResolution());
        dto.setCategory(artwork.getCategory());
        dto.setMedium(artwork.getMedium());
        dto.setFileFormat(artwork.getFileFormat());
        dto.setFileSizeMb(artwork.getFileSizeMb());
        dto.setWidthPx(artwork.getWidthPx());
        dto.setHeightPx(artwork.getHeightPx());
        dto.setPrice(artwork.getPrice());
        dto.setForSale(artwork.isForSale());
        dto.setActive(artwork.isActive());
        dto.setTags(artwork.getTags());
        dto.setCollectionId(artwork.getCollection() != null ? artwork.getCollection().getId() : null);
        return dto;
    }
}
