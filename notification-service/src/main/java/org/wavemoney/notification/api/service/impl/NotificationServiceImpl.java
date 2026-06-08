package org.wavemoney.notification.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.wavemoney.notification.api.dto.event.TransactionEvent;
import org.wavemoney.notification.api.entity.Notification;
import org.wavemoney.notification.api.repository.NotificationRepository;
import org.wavemoney.notification.api.service.NotificationService;

import java.time.Instant;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public Notification handleTransactionEvent(TransactionEvent event) {
        Notification notification = Notification.builder()
                .transactionId(event.transactionId())
                .recipient(event.to())
                .type("TRANSACTION")
                .message(buildMessage(event))
                .status(event.status())
                .createdAt(Instant.now())
                .build();

        Notification saved = notificationRepository.save(notification);
        log.debug("Saved notification id={} for transactionId={}", saved.getId(), event.transactionId());
        return saved;
    }

    private String buildMessage(TransactionEvent event) {
        return String.format(
                "Transaction %s of amount %s from %s to %s is %s",
                event.transactionId(),
                event.amount(),
                event.from(),
                event.to(),
                event.status()
        );
    }
}
