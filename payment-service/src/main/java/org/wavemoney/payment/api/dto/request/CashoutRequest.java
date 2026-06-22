package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record CashoutRequest(
        @NotNull
        String from,

        @NotNull
        Double amount
) {
}
