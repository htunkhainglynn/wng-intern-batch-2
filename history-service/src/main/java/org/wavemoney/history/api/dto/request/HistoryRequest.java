package org.wavemoney.history.api.dto.request;

public record HistoryRequest(
        String transactionId,
        String type,
        String amount,
        String currency,
        String sender,
        String recipient,
        String timestamp) {
}