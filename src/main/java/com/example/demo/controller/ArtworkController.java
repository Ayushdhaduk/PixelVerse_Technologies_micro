package com.example.demo.controller;

import com.example.demo.dto.ArtworkDTO;
import com.example.demo.model.Artwork;
import com.example.demo.service.ArtworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artworks")
@CrossOrigin(origins = "*")
public class ArtworkController {

    @Autowired
    private ArtworkService artworkService;

    // POST /api/artworks — Upload Artwork
    @PostMapping
    public ResponseEntity<ArtworkDTO> createArtwork(@RequestBody ArtworkDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(artworkService.createArtwork(dto));
    }

    // GET /api/artworks — All Artworks
    @GetMapping
    public ResponseEntity<List<ArtworkDTO>> getAllArtworks() {
        return ResponseEntity.ok(artworkService.getAllArtworks());
    }

    // GET /api/artworks/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ArtworkDTO> getArtworkById(@PathVariable Long id) {
        return ResponseEntity.ok(artworkService.getArtworkById(id));
    }

    // GET /api/artworks/artist/{artistId}
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<ArtworkDTO>> getByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(artworkService.getArtworksByArtist(artistId));
    }

    // GET /api/artworks/category/{category}
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ArtworkDTO>> getByCategory(@PathVariable Artwork.ArtworkCategory category) {
        return ResponseEntity.ok(artworkService.getArtworksByCategory(category));
    }

    // GET /api/artworks/resolution/{resolution}
    @GetMapping("/resolution/{resolution}")
    public ResponseEntity<List<ArtworkDTO>> getByResolution(@PathVariable Artwork.ResolutionType resolution) {
        return ResponseEntity.ok(artworkService.getArtworksByResolution(resolution));
    }

    // GET /api/artworks/for-sale
    @GetMapping("/for-sale")
    public ResponseEntity<List<ArtworkDTO>> getForSale() {
        return ResponseEntity.ok(artworkService.getArtworksForSale());
    }

    // GET /api/artworks/collection/{collectionId}
    @GetMapping("/collection/{collectionId}")
    public ResponseEntity<List<ArtworkDTO>> getByCollection(@PathVariable Long collectionId) {
        return ResponseEntity.ok(artworkService.getArtworksByCollection(collectionId));
    }

    // GET /api/artworks/search?keyword=abc
    @GetMapping("/search")
    public ResponseEntity<List<ArtworkDTO>> searchByTitle(@RequestParam String keyword) {
        return ResponseEntity.ok(artworkService.searchByTitle(keyword));
    }

    // PUT /api/artworks/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ArtworkDTO> updateArtwork(@PathVariable Long id, @RequestBody ArtworkDTO dto) {
        return ResponseEntity.ok(artworkService.updateArtwork(id, dto));
    }

    // DELETE /api/artworks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtwork(@PathVariable Long id) {
        return ResponseEntity.ok(artworkService.deleteArtwork(id));
    }
}
