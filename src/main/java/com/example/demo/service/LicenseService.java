package com.example.demo.service;

import com.example.demo.dto.LicenseDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.License;
import com.example.demo.repository.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    // Create License Request
    public LicenseDTO createLicense(LicenseDTO dto) {
        License license = mapToEntity(dto);
        license.setStatus(License.LicenseStatus.PENDING);
        License saved = licenseRepository.save(license);
        return mapToDTO(saved);
    }

    // Get All Licenses
    public List<LicenseDTO> getAllLicenses() {
        return licenseRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get License by ID
    public LicenseDTO getLicenseById(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        return mapToDTO(license);
    }

    // Get Licenses by Client
    public List<LicenseDTO> getLicensesByClient(Long clientId) {
        return licenseRepository.findByClientId(clientId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Licenses by Artist
    public List<LicenseDTO> getLicensesByArtist(Long artistId) {
        return licenseRepository.findByArtistId(artistId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Licenses by Artwork
    public List<LicenseDTO> getLicensesByArtwork(Long artworkId) {
        return licenseRepository.findByArtworkId(artworkId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Licenses by Status
    public List<LicenseDTO> getLicensesByStatus(License.LicenseStatus status) {
        return licenseRepository.findByStatus(status).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Licenses by Type
    public List<LicenseDTO> getLicensesByType(License.LicenseType type) {
        return licenseRepository.findByLicenseType(type).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Expired Licenses
    public List<LicenseDTO> getExpiredLicenses() {
        return licenseRepository.findByExpiryDateBefore(LocalDate.now())
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Licenses expiring in next N days (for renewal alerts)
    public List<LicenseDTO> getLicensesExpiringInDays(int days) {
        LocalDate now = LocalDate.now();
        LocalDate upcoming = now.plusDays(days);
        return licenseRepository.findByExpiryDateBetween(now, upcoming)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Approve License — sets status APPROVED and auto-generates certificate
    public LicenseDTO approveLicense(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        if (license.getStatus() != License.LicenseStatus.PENDING) {
            throw new IllegalStateException("Only PENDING licenses can be approved.");
        }
        license.setStatus(License.LicenseStatus.APPROVED);
        license.setStartDate(LocalDate.now());
        // Auto-generate a certificate URL (placeholder — integrate PDF lib for actual generation)
        license.setCertificateUrl("https://pixelverse.com/certificates/license-" + id + ".pdf");
        return mapToDTO(licenseRepository.save(license));
    }

    // Reject License
    public LicenseDTO rejectLicense(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        if (license.getStatus() != License.LicenseStatus.PENDING) {
            throw new IllegalStateException("Only PENDING licenses can be rejected.");
        }
        license.setStatus(License.LicenseStatus.REJECTED);
        return mapToDTO(licenseRepository.save(license));
    }

    // Activate License (after payment confirmed)
    public LicenseDTO activateLicense(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        if (license.getStatus() != License.LicenseStatus.APPROVED) {
            throw new IllegalStateException("Only APPROVED licenses can be activated.");
        }
        license.setStatus(License.LicenseStatus.ACTIVE);
        return mapToDTO(licenseRepository.save(license));
    }

    // Cancel License
    public LicenseDTO cancelLicense(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        license.setStatus(License.LicenseStatus.CANCELLED);
        return mapToDTO(licenseRepository.save(license));
    }

    // Update License
    public LicenseDTO updateLicense(Long id, LicenseDTO dto) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        if (dto.getTitle() != null) license.setTitle(dto.getTitle());
        if (dto.getDescription() != null) license.setDescription(dto.getDescription());
        if (dto.getPrice() != null) license.setPrice(dto.getPrice());
        if (dto.getExpiryDate() != null) license.setExpiryDate(dto.getExpiryDate());
        if (dto.getUsageTerms() != null) license.setUsageTerms(dto.getUsageTerms());
        if (dto.getTerritory() != null) license.setTerritory(dto.getTerritory());
        if (dto.getMaxUsageCount() != null) license.setMaxUsageCount(dto.getMaxUsageCount());
        license.setAllowsModification(dto.isAllowsModification());
        license.setAllowsResale(dto.isAllowsResale());
        license.setAllowsAttribution(dto.isAllowsAttribution());
        return mapToDTO(licenseRepository.save(license));
    }

    // Delete License
    public String deleteLicense(Long id) {
        licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        licenseRepository.deleteById(id);
        return "License with id " + id + " deleted successfully.";
    }

    // Get certificate URL for download
    public String getCertificateUrl(Long id) {
        License license = licenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("License not found with id: " + id));
        if (license.getCertificateUrl() == null) {
            throw new IllegalStateException("Certificate not yet generated for license id: " + id);
        }
        return license.getCertificateUrl();
    }

    // --- Mapping ---
    private License mapToEntity(LicenseDTO dto) {
        License license = new License();
        license.setTitle(dto.getTitle());
        license.setDescription(dto.getDescription());
        license.setArtworkId(dto.getArtworkId());
        license.setArtistId(dto.getArtistId());
        license.setClientId(dto.getClientId());
        license.setLicenseType(dto.getLicenseType());
        license.setPrice(dto.getPrice());
        license.setStartDate(dto.getStartDate());
        license.setExpiryDate(dto.getExpiryDate());
        license.setUsageTerms(dto.getUsageTerms());
        license.setTerritory(dto.getTerritory());
        license.setAllowsModification(dto.isAllowsModification());
        license.setAllowsResale(dto.isAllowsResale());
        license.setAllowsAttribution(dto.isAllowsAttribution());
        license.setMaxUsageCount(dto.getMaxUsageCount());
        return license;
    }

    private LicenseDTO mapToDTO(License license) {
        LicenseDTO dto = new LicenseDTO();
        dto.setId(license.getId());
        dto.setTitle(license.getTitle());
        dto.setDescription(license.getDescription());
        dto.setArtworkId(license.getArtworkId());
        dto.setArtistId(license.getArtistId());
        dto.setClientId(license.getClientId());
        dto.setLicenseType(license.getLicenseType());
        dto.setStatus(license.getStatus());
        dto.setPrice(license.getPrice());
        dto.setStartDate(license.getStartDate());
        dto.setExpiryDate(license.getExpiryDate());
        dto.setCertificateUrl(license.getCertificateUrl());
        dto.setUsageTerms(license.getUsageTerms());
        dto.setTerritory(license.getTerritory());
        dto.setAllowsModification(license.isAllowsModification());
        dto.setAllowsResale(license.isAllowsResale());
        dto.setAllowsAttribution(license.isAllowsAttribution());
        dto.setMaxUsageCount(license.getMaxUsageCount());
        return dto;
    }
}
