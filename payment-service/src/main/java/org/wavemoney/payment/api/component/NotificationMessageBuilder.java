package org.wavemoney.payment.api.component;

import org.springframework.stereotype.Component;
import org.wavemoney.payment.api.dto.event.NotificationEvent;

@Component
public class NotificationMessageBuilder {

    public NotificationEvent buildWelcomeEvent(String phone, String status) {
        return NotificationEvent.builder()
                .phone(phone)
                .message("Welcome to Lumen! Your account has been successfully created.")
                .type("WELCOME")
                .status(status)
                .build();
    }

    public NotificationEvent buildKycStatusEvent(String phone, String status) {
        String message = switch (status) {
            case "PENDING" -> "Your KYC documents have been received and are under review.";
            case "APPROVED" -> "Congratulations! Your KYC has been approved.";
            case "REJECTED" -> "Sorry, your KYC application was rejected. Please review and try again.";
            default -> "Your KYC status has been updated to: " + status;
        };

        return NotificationEvent.builder()
                .phone(phone)
                .message(message)
                .type("KYC_UPDATE")
                .status(status)
                .build();
    }
}