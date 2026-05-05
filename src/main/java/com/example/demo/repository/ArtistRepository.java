package com.example.demo.repository;

import com.example.demo.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {

    Optional<Artist> findByEmail(String email);

    List<Artist> findByRole(Artist.Role role);

    List<Artist> findBySpecializationContainingIgnoreCase(String specialization);

    List<Artist> findByCountry(String country);

    boolean existsByEmail(String email);

    List<Artist> findByActiveTrue();
}
