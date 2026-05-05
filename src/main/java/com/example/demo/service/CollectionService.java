package com.example.demo.service;

import com.example.demo.dto.CollectionDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Collection;
import com.example.demo.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CollectionService {

    @Autowired
    private CollectionRepository collectionRepository;

    public CollectionDTO createCollection(CollectionDTO dto) {
        Collection collection = mapToEntity(dto);
        return mapToDTO(collectionRepository.save(collection));
    }

    public List<CollectionDTO> getAllCollections() {
        return collectionRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CollectionDTO getCollectionById(Long id) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found with id: " + id));
        return mapToDTO(collection);
    }

    public List<CollectionDTO> getCollectionsByArtist(Long artistId) {
        return collectionRepository.findByArtistId(artistId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public List<CollectionDTO> searchCollections(String keyword) {
        return collectionRepository.findByNameContainingIgnoreCase(keyword).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public CollectionDTO updateCollection(Long id, CollectionDTO dto) {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found with id: " + id));
        if (dto.getName() != null) collection.setName(dto.getName());
        if (dto.getDescription() != null) collection.setDescription(dto.getDescription());
        if (dto.getCoverImageUrl() != null) collection.setCoverImageUrl(dto.getCoverImageUrl());
        collection.setActive(dto.isActive());
        return mapToDTO(collectionRepository.save(collection));
    }

    public String deleteCollection(Long id) {
        collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found with id: " + id));
        collectionRepository.deleteById(id);
        return "Collection with id " + id + " deleted successfully.";
    }

    private Collection mapToEntity(CollectionDTO dto) {
        Collection collection = new Collection();
        collection.setName(dto.getName());
        collection.setDescription(dto.getDescription());
        collection.setArtistId(dto.getArtistId());
        collection.setCoverImageUrl(dto.getCoverImageUrl());
        collection.setActive(dto.isActive());
        return collection;
    }

    private CollectionDTO mapToDTO(Collection collection) {
        CollectionDTO dto = new CollectionDTO();
        dto.setId(collection.getId());
        dto.setName(collection.getName());
        dto.setDescription(collection.getDescription());
        dto.setArtistId(collection.getArtistId());
        dto.setCoverImageUrl(collection.getCoverImageUrl());
        dto.setActive(collection.isActive());
        return dto;
    }
}
