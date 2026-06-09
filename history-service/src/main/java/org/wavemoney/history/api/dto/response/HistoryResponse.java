package org.wavemoney.history.api.dto.response;

public record HistoryResponse(
        String id,
        String transactionId,
        String type,
        String amount,
        String currency,
        String sender,
        String recipient,
        String timestamp) {
}