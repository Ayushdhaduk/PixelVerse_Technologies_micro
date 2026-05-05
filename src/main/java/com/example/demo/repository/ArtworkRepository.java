package com.example.demo.repository;

import com.example.demo.model.Artwork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtworkRepository extends JpaRepository<Artwork, Long> {

    List<Artwork> findByArtistId(Long artistId);

    List<Artwork> findByCategory(Artwork.ArtworkCategory category);

    List<Artwork> findByResolution(Artwork.ResolutionType resolution);

    List<Artwork> findByForSaleTrue();

    List<Artwork> findByActiveTrue();

    List<Artwork> findByCollectionId(Long collectionId);

    List<Artwork> findByTitleContainingIgnoreCase(String keyword);
}
