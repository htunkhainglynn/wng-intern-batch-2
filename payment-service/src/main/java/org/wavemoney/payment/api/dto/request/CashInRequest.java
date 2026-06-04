package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record CashInRequest(
        @NotNull
        String from,

        @NotNull
        String to,

        @NotNull
        Double amount
) {
}
