package com.example.demo.controller;

import com.example.demo.dto.CollectionDTO;
import com.example.demo.service.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collections")
@CrossOrigin(origins = "*")
public class CollectionController {

    @Autowired
    private CollectionService collectionService;

    // POST /api/collections
    @PostMapping
    public ResponseEntity<CollectionDTO> createCollection(@RequestBody CollectionDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(collectionService.createCollection(dto));
    }

    // GET /api/collections
    @GetMapping
    public ResponseEntity<List<CollectionDTO>> getAllCollections() {
        return ResponseEntity.ok(collectionService.getAllCollections());
    }

    // GET /api/collections/{id}
    @GetMapping("/{id}")
    public ResponseEntity<CollectionDTO> getCollectionById(@PathVariable Long id) {
        return ResponseEntity.ok(collectionService.getCollectionById(id));
    }

    // GET /api/collections/artist/{artistId}
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<CollectionDTO>> getByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(collectionService.getCollectionsByArtist(artistId));
    }

    // GET /api/collections/search?keyword=abc
    @GetMapping("/search")
    public ResponseEntity<List<CollectionDTO>> search(@RequestParam String keyword) {
        return ResponseEntity.ok(collectionService.searchCollections(keyword));
    }

    // PUT /api/collections/{id}
    @PutMapping("/{id}")
    public ResponseEntity<CollectionDTO> updateCollection(@PathVariable Long id, @RequestBody CollectionDTO dto) {
        return ResponseEntity.ok(collectionService.updateCollection(id, dto));
    }

    // DELETE /api/collections/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCollection(@PathVariable Long id) {
        return ResponseEntity.ok(collectionService.deleteCollection(id));
    }
}
