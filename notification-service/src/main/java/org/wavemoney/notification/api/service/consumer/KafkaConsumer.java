package org.wavemoney.notification.api.service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.wavemoney.notification.api.dto.event.TransactionEvent;
import org.wavemoney.notification.api.service.NotificationService;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final NotificationService notificationService;

    @KafkaListener(
            topics = "${app.kafka.topics.transaction-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onTransactionEvent(TransactionEvent event) {
        log.info("Received transaction event: {}", event);
        notificationService.handleTransactionEvent(event);
    }
}
