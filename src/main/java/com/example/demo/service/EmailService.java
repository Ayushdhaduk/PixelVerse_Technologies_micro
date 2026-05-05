package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired(required = false)
    private JavaMailSender mailSender;

    /**
     * Sends a plain-text email notification.
     * If mail sender is not configured, it logs to console instead.
     */
    public void sendEmail(String to, String subject, String body) {
        if (mailSender != null) {
            try {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom("noreply@pixelverse.com");
                message.setTo(to);
                message.setSubject(subject);
                message.setText(body);
                mailSender.send(message);
                System.out.println("[EMAIL SENT] To: " + to + " | Subject: " + subject);
            } catch (Exception e) {
                System.err.println("[EMAIL FAILED] " + e.getMessage());
            }
        } else {
            // Fallback: log to console (for dev without SMTP config)
            System.out.println("==== [EMAIL SIMULATION] ====");
            System.out.println("To      : " + to);
            System.out.println("Subject : " + subject);
            System.out.println("Body    : " + body);
            System.out.println("============================");
        }
    }

    /**
     * Sends a License Approval email to client.
     */
    public void sendLicenseApprovalEmail(String clientEmail, Long licenseId, String certificateUrl) {
        String subject = "PixelVerse - Your License #" + licenseId + " Has Been Approved!";
        String body = "Dear Client,\n\n"
                + "Your artwork license request (ID: " + licenseId + ") has been approved.\n\n"
                + "Download your certificate here:\n" + certificateUrl + "\n\n"
                + "Thank you for choosing PixelVerse!\n\n"
                + "Best Regards,\nPixelVerse Team";
        sendEmail(clientEmail, subject, body);
    }

    /**
     * Sends a License Expiry Reminder email.
     */
    public void sendLicenseExpiryReminderEmail(String recipientEmail, Long licenseId, long daysLeft) {
        String subject = "PixelVerse - License #" + licenseId + " Expiring in " + daysLeft + " Day(s)";
        String body = "Dear User,\n\n"
                + "This is a reminder that your artwork license (ID: " + licenseId + ") "
                + "will expire in " + daysLeft + " day(s).\n\n"
                + "Please visit the portal to renew your license and continue using the artwork.\n\n"
                + "Best Regards,\nPixelVerse Team";
        sendEmail(recipientEmail, subject, body);
    }

    /**
     * Sends a Purchase Confirmation email.
     */
    public void sendPurchaseConfirmationEmail(String clientEmail, Long licenseId) {
        String subject = "PixelVerse - Purchase Confirmed! License #" + licenseId;
        String body = "Dear Client,\n\n"
                + "Your purchase of artwork license (ID: " + licenseId + ") has been confirmed.\n\n"
                + "Your license is now active. Access your dashboard for details.\n\n"
                + "Thank you for your purchase!\n\n"
                + "Best Regards,\nPixelVerse Team";
        sendEmail(clientEmail, subject, body);
    }

    /**
     * Sends a New License Request alert to an artist.
     */
    public void sendNewLicenseRequestEmail(String artistEmail, Long licenseId) {
        String subject = "PixelVerse - New License Request #" + licenseId;
        String body = "Dear Artist,\n\n"
                + "You have received a new license request for your artwork (License ID: " + licenseId + ").\n\n"
                + "Please log in to your dashboard to review and respond.\n\n"
                + "Best Regards,\nPixelVerse Team";
        sendEmail(artistEmail, subject, body);
    }
}
