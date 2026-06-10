package org.wavemoney.history.api.service.consumer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.wavemoney.history.api.service.HistoryService;
import org.wavemoney.history.api.dto.event.TransactionEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final HistoryService historyService;

    @KafkaListener(
            topics = "${app.kafka.topics.transaction-events}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void onSentEvent(TransactionEvent event) {
        log.info("Received transaction event: {}", event);
        historyService.handleHistoryEvent(event);
    }

}
