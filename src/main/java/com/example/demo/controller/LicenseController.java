package com.example.demo.controller;

import com.example.demo.dto.LicenseDTO;
import com.example.demo.model.License;
import com.example.demo.service.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/licenses")
@CrossOrigin(origins = "*")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    // POST /api/licenses — Create License Request
    @PostMapping
    public ResponseEntity<LicenseDTO> createLicense(@RequestBody LicenseDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(licenseService.createLicense(dto));
    }

    // GET /api/licenses — All Licenses
    @GetMapping
    public ResponseEntity<List<LicenseDTO>> getAllLicenses() {
        return ResponseEntity.ok(licenseService.getAllLicenses());
    }

    // GET /api/licenses/{id}
    @GetMapping("/{id}")
    public ResponseEntity<LicenseDTO> getLicenseById(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.getLicenseById(id));
    }

    // GET /api/licenses/client/{clientId}
    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<LicenseDTO>> getLicensesByClient(@PathVariable Long clientId) {
        return ResponseEntity.ok(licenseService.getLicensesByClient(clientId));
    }

    // GET /api/licenses/artist/{artistId}
    @GetMapping("/artist/{artistId}")
    public ResponseEntity<List<LicenseDTO>> getLicensesByArtist(@PathVariable Long artistId) {
        return ResponseEntity.ok(licenseService.getLicensesByArtist(artistId));
    }

    // GET /api/licenses/artwork/{artworkId}
    @GetMapping("/artwork/{artworkId}")
    public ResponseEntity<List<LicenseDTO>> getLicensesByArtwork(@PathVariable Long artworkId) {
        return ResponseEntity.ok(licenseService.getLicensesByArtwork(artworkId));
    }

    // GET /api/licenses/status/{status}
    @GetMapping("/status/{status}")
    public ResponseEntity<List<LicenseDTO>> getLicensesByStatus(@PathVariable License.LicenseStatus status) {
        return ResponseEntity.ok(licenseService.getLicensesByStatus(status));
    }

    // GET /api/licenses/type/{type}
    @GetMapping("/type/{type}")
    public ResponseEntity<List<LicenseDTO>> getLicensesByType(@PathVariable License.LicenseType type) {
        return ResponseEntity.ok(licenseService.getLicensesByType(type));
    }

    // GET /api/licenses/expired
    @GetMapping("/expired")
    public ResponseEntity<List<LicenseDTO>> getExpiredLicenses() {
        return ResponseEntity.ok(licenseService.getExpiredLicenses());
    }

    // GET /api/licenses/expiring?days=30
    @GetMapping("/expiring")
    public ResponseEntity<List<LicenseDTO>> getLicensesExpiringSoon(@RequestParam(defaultValue = "30") int days) {
        return ResponseEntity.ok(licenseService.getLicensesExpiringInDays(days));
    }

    // PATCH /api/licenses/{id}/approve
    @PatchMapping("/{id}/approve")
    public ResponseEntity<LicenseDTO> approveLicense(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.approveLicense(id));
    }

    // PATCH /api/licenses/{id}/reject
    @PatchMapping("/{id}/reject")
    public ResponseEntity<LicenseDTO> rejectLicense(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.rejectLicense(id));
    }

    // PATCH /api/licenses/{id}/activate
    @PatchMapping("/{id}/activate")
    public ResponseEntity<LicenseDTO> activateLicense(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.activateLicense(id));
    }

    // PATCH /api/licenses/{id}/cancel
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<LicenseDTO> cancelLicense(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.cancelLicense(id));
    }

    // GET /api/licenses/{id}/certificate — Download Certificate URL
    @GetMapping("/{id}/certificate")
    public ResponseEntity<String> getCertificate(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.getCertificateUrl(id));
    }

    // PUT /api/licenses/{id}
    @PutMapping("/{id}")
    public ResponseEntity<LicenseDTO> updateLicense(@PathVariable Long id, @RequestBody LicenseDTO dto) {
        return ResponseEntity.ok(licenseService.updateLicense(id, dto));
    }

    // DELETE /api/licenses/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLicense(@PathVariable Long id) {
        return ResponseEntity.ok(licenseService.deleteLicense(id));
    }
}
