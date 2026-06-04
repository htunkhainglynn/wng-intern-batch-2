package org.wavemoney.payment.api.dto.response;

public record TransactionResponse(
        String transactionId,
        String from,
        String to,
        Double amount,
        String status,
        String transactionType,
        String transactionTime
) {
}
