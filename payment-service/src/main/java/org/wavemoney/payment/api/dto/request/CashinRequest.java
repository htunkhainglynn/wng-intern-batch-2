package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotNull;

public record CashinRequest(
        @NotNull
        String to,

        @NotNull
        Double amount
) {
}
