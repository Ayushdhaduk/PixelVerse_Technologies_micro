package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "licenses")
public class License {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    private Long artworkId;    // Reference to Portfolio Service
    private Long artistId;     // Reference to Artist Service
    private Long clientId;     // Client who holds the license

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LicenseType licenseType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LicenseStatus status;

    private Double price;
    private LocalDate startDate;
    private LocalDate expiryDate;

    private String certificateUrl;       // Generated PDF certificate URL
    private String usageTerms;
    private String territory;            // e.g., Global, India, USA
    private boolean allowsModification;
    private boolean allowsResale;
    private boolean allowsAttribution;
    private Integer maxUsageCount;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public enum LicenseType {
        PERSONAL, COMMERCIAL, EXCLUSIVE, EDITORIAL, PRINT_ONLY, DIGITAL_ONLY
    }

    public enum LicenseStatus {
        PENDING, APPROVED, ACTIVE, REJECTED, EXPIRED, CANCELLED
    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (status == null) status = LicenseStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Long getArtworkId() { return artworkId; }
    public void setArtworkId(Long artworkId) { this.artworkId = artworkId; }

    public Long getArtistId() { return artistId; }
    public void setArtistId(Long artistId) { this.artistId = artistId; }

    public Long getClientId() { return clientId; }
    public void setClientId(Long clientId) { this.clientId = clientId; }

    public LicenseType getLicenseType() { return licenseType; }
    public void setLicenseType(LicenseType licenseType) { this.licenseType = licenseType; }

    public LicenseStatus getStatus() { return status; }
    public void setStatus(LicenseStatus status) { this.status = status; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getExpiryDate() { return expiryDate; }
    public void setExpiryDate(LocalDate expiryDate) { this.expiryDate = expiryDate; }

    public String getCertificateUrl() { return certificateUrl; }
    public void setCertificateUrl(String certificateUrl) { this.certificateUrl = certificateUrl; }

    public String getUsageTerms() { return usageTerms; }
    public void setUsageTerms(String usageTerms) { this.usageTerms = usageTerms; }

    public String getTerritory() { return territory; }
    public void setTerritory(String territory) { this.territory = territory; }

    public boolean isAllowsModification() { return allowsModification; }
    public void setAllowsModification(boolean allowsModification) { this.allowsModification = allowsModification; }

    public boolean isAllowsResale() { return allowsResale; }
    public void setAllowsResale(boolean allowsResale) { this.allowsResale = allowsResale; }

    public boolean isAllowsAttribution() { return allowsAttribution; }
    public void setAllowsAttribution(boolean allowsAttribution) { this.allowsAttribution = allowsAttribution; }

    public Integer getMaxUsageCount() { return maxUsageCount; }
    public void setMaxUsageCount(Integer maxUsageCount) { this.maxUsageCount = maxUsageCount; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
