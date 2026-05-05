package com.example.demo.service;

import com.example.demo.dto.NotificationDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Create / Send Notification
    public NotificationDTO createNotification(NotificationDTO dto) {
        Notification notification = mapToEntity(dto);
        notification.setStatus(Notification.NotificationStatus.SENT);
        Notification saved = notificationRepository.save(notification);
        return mapToDTO(saved);
    }

    // Get All Notifications
    public List<NotificationDTO> getAllNotifications() {
        return notificationRepository.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Notification by ID
    public NotificationDTO getNotificationById(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        return mapToDTO(notification);
    }

    // Get Notifications for a Recipient (sorted by date desc)
    public List<NotificationDTO> getNotificationsForRecipient(Long recipientId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(recipientId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Unread Notifications for a Recipient
    public List<NotificationDTO> getUnreadNotifications(Long recipientId) {
        return notificationRepository.findByRecipientIdAndIsReadFalse(recipientId)
                .stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Unread Count
    public Map<String, Long> getUnreadCount(Long recipientId) {
        long count = notificationRepository.countByRecipientIdAndIsReadFalse(recipientId);
        return Map.of("recipientId", recipientId, "unreadCount", count);
    }

    // Get Notifications by Type
    public List<NotificationDTO> getByType(Notification.NotificationType type) {
        return notificationRepository.findByType(type).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Get Notifications by Reference (e.g., all notifications for a licenseId)
    public List<NotificationDTO> getByReference(Long referenceId) {
        return notificationRepository.findByReferenceId(referenceId).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    // Mark Notification as Read
    public NotificationDTO markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notification.setRead(true);
        return mapToDTO(notificationRepository.save(notification));
    }

    // Mark All as Read for Recipient
    public String markAllAsRead(Long recipientId) {
        List<Notification> unread = notificationRepository.findByRecipientIdAndIsReadFalse(recipientId);
        unread.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unread);
        return "Marked " + unread.size() + " notifications as read for recipient: " + recipientId;
    }

    // Send License Request Notification to Artist
    public NotificationDTO sendLicenseRequestAlert(Long artistId, String artistEmail, Long licenseId) {
        NotificationDTO dto = new NotificationDTO();
        dto.setTitle("New License Request");
        dto.setMessage("You have received a new license request. License ID: " + licenseId);
        dto.setRecipientId(artistId);
        dto.setRecipientEmail(artistEmail);
        dto.setReferenceId(licenseId);
        dto.setType(Notification.NotificationType.LICENSE_REQUEST);
        dto.setChannel(Notification.NotificationChannel.IN_APP);
        return createNotification(dto);
    }

    // Send License Approved Notification to Client
    public NotificationDTO sendLicenseApprovedAlert(Long clientId, String clientEmail, Long licenseId) {
        NotificationDTO dto = new NotificationDTO();
        dto.setTitle("License Approved!");
        dto.setMessage("Your license request has been approved. License ID: " + licenseId +
                ". Download your certificate from the portal.");
        dto.setRecipientId(clientId);
        dto.setRecipientEmail(clientEmail);
        dto.setReferenceId(licenseId);
        dto.setType(Notification.NotificationType.LICENSE_APPROVED);
        dto.setChannel(Notification.NotificationChannel.EMAIL);
        return createNotification(dto);
    }

    // Send License Expiry Alert
    public NotificationDTO sendLicenseExpiryAlert(Long recipientId, String email, Long licenseId, long daysLeft) {
        NotificationDTO dto = new NotificationDTO();
        dto.setTitle("License Expiring Soon");
        dto.setMessage("Your license (ID: " + licenseId + ") will expire in " + daysLeft + " day(s). Please renew to continue using the artwork.");
        dto.setRecipientId(recipientId);
        dto.setRecipientEmail(email);
        dto.setReferenceId(licenseId);
        dto.setType(Notification.NotificationType.LICENSE_RENEWAL);
        dto.setChannel(Notification.NotificationChannel.EMAIL);
        return createNotification(dto);
    }

    // Send Purchase Confirmed Notification
    public NotificationDTO sendPurchaseConfirmedAlert(Long clientId, String clientEmail, Long licenseId) {
        NotificationDTO dto = new NotificationDTO();
        dto.setTitle("Purchase Confirmed");
        dto.setMessage("Your artwork license purchase is confirmed. License ID: " + licenseId);
        dto.setRecipientId(clientId);
        dto.setRecipientEmail(clientEmail);
        dto.setReferenceId(licenseId);
        dto.setType(Notification.NotificationType.PURCHASE_CONFIRMED);
        dto.setChannel(Notification.NotificationChannel.EMAIL);
        return createNotification(dto);
    }

    // Delete Notification
    public String deleteNotification(Long id) {
        notificationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Notification not found with id: " + id));
        notificationRepository.deleteById(id);
        return "Notification with id " + id + " deleted.";
    }

    // --- Mapping ---
    private Notification mapToEntity(NotificationDTO dto) {
        Notification n = new Notification();
        n.setTitle(dto.getTitle());
        n.setMessage(dto.getMessage());
        n.setRecipientId(dto.getRecipientId());
        n.setRecipientEmail(dto.getRecipientEmail());
        n.setReferenceId(dto.getReferenceId());
        n.setType(dto.getType());
        n.setChannel(dto.getChannel() != null ? dto.getChannel() : Notification.NotificationChannel.IN_APP);
        n.setRead(dto.isRead());
        return n;
    }

    private NotificationDTO mapToDTO(Notification n) {
        NotificationDTO dto = new NotificationDTO();
        dto.setId(n.getId());
        dto.setTitle(n.getTitle());
        dto.setMessage(n.getMessage());
        dto.setRecipientId(n.getRecipientId());
        dto.setRecipientEmail(n.getRecipientEmail());
        dto.setReferenceId(n.getReferenceId());
        dto.setType(n.getType());
        dto.setChannel(n.getChannel());
        dto.setStatus(n.getStatus());
        dto.setRead(n.isRead());
        return dto;
    }
}
