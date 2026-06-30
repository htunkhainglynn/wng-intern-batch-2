package org.wavemoney.notification.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.wavemoney.notification.api.dto.event.NotificationEvent;
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
    public List<Notification> handleTransferEvent(TransactionEvent event) {
        List<Notification> saved = new ArrayList<>();
        saveIfAbsent(buildSenderNotification(event)).ifPresent(saved::add);
        saveIfAbsent(buildReceiverNotification(event)).ifPresent(saved::add);
        log.debug("Saved transfer notifications for transactionId={}: {}", event.transactionId(), saved);
        return saved;
    }

    @Override
    public Optional<Notification> handleCashinEvent(TransactionEvent event) {
        Optional<Notification> saved = saveIfAbsent(buildCashinNotification(event));
        log.debug("Saved cashin notification for transactionId={}: {}", event.transactionId(), saved);
        return saved;
    }

    public Optional<Notification> handleCashoutEvent(TransactionEvent event) {
        Optional<Notification> saved = saveIfAbsent(buildCashoutNotification(event));
        log.debug("Saved cashout notification for transactionId={}: {}", event.transactionId(), saved);
        return saved;
    }

    @Override
    public void handleNotificationEvent(NotificationEvent event) {
        Notification notification = Notification.builder()
                .recipient(event.phone())
                .message(event.message())
                .type(event.type())
                .status(event.status())
                .createdAt(Instant.now())
                .build();
        notificationRepository.save(notification);
        log.info("Saved notification for phone={} type={}", event.phone(), event.type());

    }

    @Override
    public List<Notification> getNotifications(String phone) {
        return notificationRepository.findByRecipient(phone);
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
                .type("SENT_MONEY")
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
                .type("RECEIVED_MONEY")
                .message(String.format("You received %s from %s. Transaction %s.",
                        event.amount(), event.from(), event.transactionId()))
                .status(event.status())
                .createdAt(Instant.now())
                .build();
    }

    private Notification buildCashinNotification(TransactionEvent event) {
        return Notification.builder()
                .transactionId(event.transactionId())
                .recipient(event.to())
                .type("CASHIN")
                .message(String.format("Successful Cashin of %s to your wallet. Transaction %s.",
                        event.amount(), event.transactionId()))
                .status(event.status())
                .createdAt(Instant.now())
                .build();
    }

    private Notification buildCashoutNotification(TransactionEvent event) {
        return Notification.builder()
                .transactionId(event.transactionId())
                .recipient(event.from())
                .type("CASHOUT")
                .message(String.format("Successful Cashout of %s from your wallet. Transaction %s.",
                        event.amount(), event.transactionId()))
                .status(event.status())
                .createdAt(Instant.now())
                .build();
    }
}
