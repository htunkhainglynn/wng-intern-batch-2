package org.wavemoney.notification.api.dto.event;

import lombok.Builder;

@Builder
public record TransactionEvent(
        String transactionId,
        String from,
        String to,
        Double amount,
        String status,
        String transactionType,
        String transactionTime
) {
}
