package com.example.demo.controller;

import com.example.demo.dto.ArtistDTO;
import com.example.demo.model.Artist;
import com.example.demo.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
@CrossOrigin(origins = "*")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // POST /api/artists — Register Artist/Client/Admin
    @PostMapping
    public ResponseEntity<ArtistDTO> createArtist(@RequestBody ArtistDTO dto) {
        ArtistDTO created = artistService.createArtist(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // GET /api/artists — Get All Artists
    @GetMapping
    public ResponseEntity<List<ArtistDTO>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    // GET /api/artists/active — Get Active Artists
    @GetMapping("/active")
    public ResponseEntity<List<ArtistDTO>> getActiveArtists() {
        return ResponseEntity.ok(artistService.getActiveArtists());
    }

    // GET /api/artists/{id} — Get Artist by ID
    @GetMapping("/{id}")
    public ResponseEntity<ArtistDTO> getArtistById(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.getArtistById(id));
    }

    // GET /api/artists/email/{email} — Get Artist by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<ArtistDTO> getArtistByEmail(@PathVariable String email) {
        return ResponseEntity.ok(artistService.getArtistByEmail(email));
    }

    // GET /api/artists/role/{role} — Filter by Role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<ArtistDTO>> getArtistsByRole(@PathVariable Artist.Role role) {
        return ResponseEntity.ok(artistService.getArtistsByRole(role));
    }

    // GET /api/artists/specialization/{specialization} — Filter by Specialization
    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<ArtistDTO>> getBySpecialization(@PathVariable String specialization) {
        return ResponseEntity.ok(artistService.getArtistsBySpecialization(specialization));
    }

    // GET /api/artists/country/{country} — Filter by Country
    @GetMapping("/country/{country}")
    public ResponseEntity<List<ArtistDTO>> getByCountry(@PathVariable String country) {
        return ResponseEntity.ok(artistService.getArtistsByCountry(country));
    }

    // PUT /api/artists/{id} — Update Artist Profile
    @PutMapping("/{id}")
    public ResponseEntity<ArtistDTO> updateArtist(@PathVariable Long id, @RequestBody ArtistDTO dto) {
        return ResponseEntity.ok(artistService.updateArtist(id, dto));
    }

    // PATCH /api/artists/{id}/deactivate — Soft Delete
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<String> deactivateArtist(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.deactivateArtist(id));
    }

    // DELETE /api/artists/{id} — Hard Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Long id) {
        return ResponseEntity.ok(artistService.deleteArtist(id));
    }
}
