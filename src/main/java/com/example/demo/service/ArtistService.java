package com.example.demo.service;

import com.example.demo.dto.ArtistDTO;
import com.example.demo.exception.DuplicateResourceException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Artist;
import com.example.demo.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    // Create Artist
    public ArtistDTO createArtist(ArtistDTO dto) {
        if (artistRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Artist with email '" + dto.getEmail() + "' already exists.");
        }
        Artist artist = mapToEntity(dto);
        Artist saved = artistRepository.save(artist);
        return mapToDTO(saved);
    }

    // Get All Artists
    public List<ArtistDTO> getAllArtists() {
        return artistRepository.findAll()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Active Artists
    public List<ArtistDTO> getActiveArtists() {
        return artistRepository.findByActiveTrue()
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Artist by ID
    public ArtistDTO getArtistById(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
        return mapToDTO(artist);
    }

    // Get Artist by Email
    public ArtistDTO getArtistByEmail(String email) {
        Artist artist = artistRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with email: " + email));
        return mapToDTO(artist);
    }

    // Get Artists by Role
    public List<ArtistDTO> getArtistsByRole(Artist.Role role) {
        return artistRepository.findByRole(role)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Artists by Specialization
    public List<ArtistDTO> getArtistsBySpecialization(String specialization) {
        return artistRepository.findBySpecializationContainingIgnoreCase(specialization)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Artists by Country
    public List<ArtistDTO> getArtistsByCountry(String country) {
        return artistRepository.findByCountry(country)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Update Artist
    public ArtistDTO updateArtist(Long id, ArtistDTO dto) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));

        if (dto.getName() != null) artist.setName(dto.getName());
        if (dto.getBio() != null) artist.setBio(dto.getBio());
        if (dto.getProfileImageUrl() != null) artist.setProfileImageUrl(dto.getProfileImageUrl());
        if (dto.getSpecialization() != null) artist.setSpecialization(dto.getSpecialization());
        if (dto.getCountry() != null) artist.setCountry(dto.getCountry());
        if (dto.getWebsite() != null) artist.setWebsite(dto.getWebsite());
        if (dto.getSkills() != null) artist.setSkills(dto.getSkills());
        if (dto.getRole() != null) artist.setRole(dto.getRole());

        Artist updated = artistRepository.save(artist);
        return mapToDTO(updated);
    }

    // Deactivate / Soft Delete
    public String deactivateArtist(Long id) {
        Artist artist = artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
        artist.setActive(false);
        artistRepository.save(artist);
        return "Artist with id " + id + " has been deactivated.";
    }

    // Hard Delete
    public String deleteArtist(Long id) {
        artistRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id: " + id));
        artistRepository.deleteById(id);
        return "Artist with id " + id + " has been deleted.";
    }

    // --- Mapping Helpers ---
    private Artist mapToEntity(ArtistDTO dto) {
        Artist artist = new Artist();
        artist.setName(dto.getName());
        artist.setEmail(dto.getEmail());
        artist.setPassword(dto.getPassword());
        artist.setRole(dto.getRole() != null ? dto.getRole() : Artist.Role.ARTIST);
        artist.setBio(dto.getBio());
        artist.setProfileImageUrl(dto.getProfileImageUrl());
        artist.setSpecialization(dto.getSpecialization());
        artist.setCountry(dto.getCountry());
        artist.setWebsite(dto.getWebsite());
        artist.setActive(dto.isActive());
        artist.setSkills(dto.getSkills());
        return artist;
    }

    private ArtistDTO mapToDTO(Artist artist) {
        ArtistDTO dto = new ArtistDTO();
        dto.setId(artist.getId());
        dto.setName(artist.getName());
        dto.setEmail(artist.getEmail());
        dto.setRole(artist.getRole());
        dto.setBio(artist.getBio());
        dto.setProfileImageUrl(artist.getProfileImageUrl());
        dto.setSpecialization(artist.getSpecialization());
        dto.setCountry(artist.getCountry());
        dto.setWebsite(artist.getWebsite());
        dto.setActive(artist.isActive());
        dto.setSkills(artist.getSkills());
        return dto;
    }
}
