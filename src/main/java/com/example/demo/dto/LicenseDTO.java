package com.example.demo.dto;

import com.example.demo.model.License;
import java.time.LocalDate;

public class LicenseDTO {

    private Long id;
    private String title;
    private String description;
    private Long artworkId;
    private Long artistId;
    private Long clientId;
    private License.LicenseType licenseType;
    private License.LicenseStatus status;
    private Double price;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private String certificateUrl;
    private String usageTerms;
    private String territory;
    private boolean allowsModification;
    private boolean allowsResale;
    private boolean allowsAttribution;
    private Integer maxUsageCount;

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

    public License.LicenseType getLicenseType() { return licenseType; }
    public void setLicenseType(License.LicenseType licenseType) { this.licenseType = licenseType; }

    public License.LicenseStatus getStatus() { return status; }
    public void setStatus(License.LicenseStatus status) { this.status = status; }

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
}
