package org.wavemoney.notification.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.wavemoney.notification.api.dto.event.TransactionEvent;
import org.wavemoney.notification.api.entity.Notification;
import org.wavemoney.notification.api.repository.NotificationRepository;
import org.wavemoney.notification.api.service.NotificationService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private static final String SYSTEM_SENDER = "SYSTEM";

    private final NotificationRepository notificationRepository;

    @Override
    public List<Notification> handleCashInEvent(TransactionEvent event) {
        List<Notification> saved = new ArrayList<>();
        saveIfAbsent(buildSenderNotification(event)).ifPresent(saved::add);
        saveIfAbsent(buildReceiverNotification(event)).ifPresent(saved::add);
        log.debug("Saved cash-in notifications for transactionId={}: {}", event.transactionId(), saved);
        return saved;
    }

    @Override
    public Optional<Notification> handleAdjustmentEvent(TransactionEvent event) {
        Optional<Notification> saved = saveIfAbsent(buildAdjustmentNotification(event));
        log.debug("Saved adjustment notification for transactionId={}: {}", event.transactionId(), saved);
        return saved;
    }

    private Optional<Notification> saveIfAbsent(Notification notification) {
        if (notification.getRecipient() == null || notification.getRecipient().isBlank()
                || SYSTEM_SENDER.equalsIgnoreCase(notification.getRecipient())) {
            return Optional.empty();
        }
        try {
            Notification persisted = notificationRepository.save(notification);
            log.debug("Saved notification id={} for transactionId={} recipient={}",
                    persisted.getId(), persisted.getTransactionId(), persisted.getRecipient());
            return Optional.of(persisted);
        } catch (DuplicateKeyException ex) {
            log.info("Notification already exists for transactionId={} recipient={}; skipping",
                    notification.getTransactionId(), notification.getRecipient());
            return Optional.empty();
        }
    }

    private Notification buildSenderNotification(TransactionEvent event) {
        return Notification.builder()
                .transactionId(event.transactionId())
                .recipient(event.from())
                .type("CASH_IN_SENT")
                .message(String.format("You sent %s to %s. Transaction %s.",
                        event.amount(), event.to(), event.transactionId()))
                .status(event.status())
                .createdAt(Instant.now())
                .build();
    }

    private Notification buildReceiverNotification(TransactionEvent event) {
        return Notification.builder()
                .transactionId(event.transactionId())
                .recipient(event.to())
                .type("CASH_IN_RECEIVED")
                .message(String.format("You received %s from %s. Transaction %s.",
                        event.amount(), event.from(), event.transactionId()))
                .status(event.status())
                .createdAt(Instant.now())
                .build();
    }

    private Notification buildAdjustmentNotification(TransactionEvent event) {
        return Notification.builder()
                .transactionId(event.transactionId())
                .recipient(event.to())
                .type("ADJUSTMENT")
                .message(String.format("Adjustment of %s applied to your wallet. Transaction %s.",
                        event.amount(), event.transactionId()))
                .status(event.status())
                .createdAt(Instant.now())
                .build();
    }
}
