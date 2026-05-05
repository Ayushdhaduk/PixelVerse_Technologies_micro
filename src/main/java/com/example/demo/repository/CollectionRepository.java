package com.example.demo.repository;

import com.example.demo.model.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<Collection, Long> {

    List<Collection> findByArtistId(Long artistId);

    List<Collection> findByActiveTrue();

    List<Collection> findByNameContainingIgnoreCase(String keyword);
}
