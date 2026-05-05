package com.example.demo.dto;

import com.example.demo.model.Notification;

public class NotificationDTO {

    private Long id;
    private String title;
    private String message;
    private Long recipientId;
    private String recipientEmail;
    private Long referenceId;
    private Notification.NotificationType type;
    private Notification.NotificationChannel channel;
    private Notification.NotificationStatus status;
    private boolean isRead;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Long getRecipientId() { return recipientId; }
    public void setRecipientId(Long recipientId) { this.recipientId = recipientId; }

    public String getRecipientEmail() { return recipientEmail; }
    public void setRecipientEmail(String recipientEmail) { this.recipientEmail = recipientEmail; }

    public Long getReferenceId() { return referenceId; }
    public void setReferenceId(Long referenceId) { this.referenceId = referenceId; }

    public Notification.NotificationType getType() { return type; }
    public void setType(Notification.NotificationType type) { this.type = type; }

    public Notification.NotificationChannel getChannel() { return channel; }
    public void setChannel(Notification.NotificationChannel channel) { this.channel = channel; }

    public Notification.NotificationStatus getStatus() { return status; }
    public void setStatus(Notification.NotificationStatus status) { this.status = status; }

    public boolean isRead() { return isRead; }
    public void setRead(boolean read) { isRead = read; }
}
