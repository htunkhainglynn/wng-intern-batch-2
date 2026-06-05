package org.wavemoney.payment.api.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record TransactionResponse(
        String transactionId,
        String from,
        String to,
        Double amount,
        String status,
        String transactionType,
        LocalDateTime transactionTime
) {

}
