package org.wavemoney.notification.api.service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.wavemoney.notification.api.dto.event.NotificationEvent;
import org.wavemoney.notification.api.dto.event.TransactionEvent;
import org.wavemoney.notification.api.service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${app.kafka.topics.notification-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onNotificationEvent(NotificationEvent event) {
        log.info("Received notification event: {}", event);
        notificationService.handleNotificationEvent(event);
    }

    @KafkaListener(
            topics = "${app.kafka.topics.transfer-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onTransferEvent(TransactionEvent event) {
        log.info("Received transfer event: {}", event);
        notificationService.handleTransferEvent(event);
    }

    @KafkaListener(
            topics = "${app.kafka.topics.cashin-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onCashinEvent(TransactionEvent event) {
        log.info("Received cashin event: {}", event);
        notificationService.handleCashinEvent(event);
    }

    @KafkaListener(
            topics = "${app.kafka.topics.cashout-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onCashoutEvent(TransactionEvent event) {
        log.info("Received cashout event: {}", event);
        notificationService.handleCashoutEvent(event);
    }

}
