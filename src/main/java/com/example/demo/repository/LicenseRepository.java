package com.example.demo.repository;

import com.example.demo.model.License;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LicenseRepository extends JpaRepository<License, Long> {

    List<License> findByClientId(Long clientId);

    List<License> findByArtistId(Long artistId);

    List<License> findByArtworkId(Long artworkId);

    List<License> findByStatus(License.LicenseStatus status);

    List<License> findByLicenseType(License.LicenseType licenseType);

    List<License> findByExpiryDateBefore(LocalDate date);

    List<License> findByExpiryDateBetween(LocalDate start, LocalDate end);

    List<License> findByArtistIdAndStatus(Long artistId, License.LicenseStatus status);

    List<License> findByClientIdAndStatus(Long clientId, License.LicenseStatus status);
}
