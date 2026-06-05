package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.wavemoney.payment.api.enums.Currency;
import org.wavemoney.payment.api.enums.WalletStatus;

@Builder
public record WalletRequest (
    String walletId,

    double balance,

    @NotNull(message = "Phone number is required")
    String phone,

    @NotNull(message = "Currency is required")
    Currency currency,

    WalletStatus status
) {
}
