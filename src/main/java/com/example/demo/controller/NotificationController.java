package com.example.demo.controller;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    // POST /api/notifications — Create Notification
    @PostMapping
    public ResponseEntity<NotificationDTO> createNotification(@RequestBody NotificationDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(notificationService.createNotification(dto));
    }

    // GET /api/notifications — All Notifications
    @GetMapping
    public ResponseEntity<List<NotificationDTO>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    // GET /api/notifications/{id}
    @GetMapping("/{id}")
    public ResponseEntity<NotificationDTO> getNotificationById(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.getNotificationById(id));
    }

    // GET /api/notifications/recipient/{recipientId}
    @GetMapping("/recipient/{recipientId}")
    public ResponseEntity<List<NotificationDTO>> getForRecipient(@PathVariable Long recipientId) {
        return ResponseEntity.ok(notificationService.getNotificationsForRecipient(recipientId));
    }

    // GET /api/notifications/recipient/{recipientId}/unread
    @GetMapping("/recipient/{recipientId}/unread")
    public ResponseEntity<List<NotificationDTO>> getUnread(@PathVariable Long recipientId) {
        return ResponseEntity.ok(notificationService.getUnreadNotifications(recipientId));
    }

    // GET /api/notifications/recipient/{recipientId}/count
    @GetMapping("/recipient/{recipientId}/count")
    public ResponseEntity<Map<String, Long>> getUnreadCount(@PathVariable Long recipientId) {
        return ResponseEntity.ok(notificationService.getUnreadCount(recipientId));
    }

    // GET /api/notifications/type/{type}
    @GetMapping("/type/{type}")
    public ResponseEntity<List<NotificationDTO>> getByType(@PathVariable Notification.NotificationType type) {
        return ResponseEntity.ok(notificationService.getByType(type));
    }

    // GET /api/notifications/reference/{referenceId}
    @GetMapping("/reference/{referenceId}")
    public ResponseEntity<List<NotificationDTO>> getByReference(@PathVariable Long referenceId) {
        return ResponseEntity.ok(notificationService.getByReference(referenceId));
    }

    // PATCH /api/notifications/{id}/read
    @PatchMapping("/{id}/read")
    public ResponseEntity<NotificationDTO> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.markAsRead(id));
    }

    // PATCH /api/notifications/recipient/{recipientId}/read-all
    @PatchMapping("/recipient/{recipientId}/read-all")
    public ResponseEntity<String> markAllAsRead(@PathVariable Long recipientId) {
        return ResponseEntity.ok(notificationService.markAllAsRead(recipientId));
    }

    // POST /api/notifications/alert/license-request
    @PostMapping("/alert/license-request")
    public ResponseEntity<NotificationDTO> sendLicenseRequestAlert(
            @RequestParam Long artistId,
            @RequestParam String artistEmail,
            @RequestParam Long licenseId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.sendLicenseRequestAlert(artistId, artistEmail, licenseId));
    }

    // POST /api/notifications/alert/license-approved
    @PostMapping("/alert/license-approved")
    public ResponseEntity<NotificationDTO> sendLicenseApprovedAlert(
            @RequestParam Long clientId,
            @RequestParam String clientEmail,
            @RequestParam Long licenseId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.sendLicenseApprovedAlert(clientId, clientEmail, licenseId));
    }

    // POST /api/notifications/alert/license-expiry
    @PostMapping("/alert/license-expiry")
    public ResponseEntity<NotificationDTO> sendLicenseExpiryAlert(
            @RequestParam Long recipientId,
            @RequestParam String email,
            @RequestParam Long licenseId,
            @RequestParam long daysLeft) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.sendLicenseExpiryAlert(recipientId, email, licenseId, daysLeft));
    }

    // POST /api/notifications/alert/purchase-confirmed
    @PostMapping("/alert/purchase-confirmed")
    public ResponseEntity<NotificationDTO> sendPurchaseConfirmedAlert(
            @RequestParam Long clientId,
            @RequestParam String clientEmail,
            @RequestParam Long licenseId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(notificationService.sendPurchaseConfirmedAlert(clientId, clientEmail, licenseId));
    }

    // DELETE /api/notifications/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNotification(@PathVariable Long id) {
        return ResponseEntity.ok(notificationService.deleteNotification(id));
    }
}
