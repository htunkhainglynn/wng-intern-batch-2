package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.wavemoney.payment.api.enums.Currency;
import org.wavemoney.payment.api.enums.WalletStatus;
import org.wavemoney.payment.api.dto.request.UserRequest;

public record WalletRequest (
    String walletId,

    double balance,

    @NotNull(message = "Phone number is required")
    String phoneNumber,

    @NotNull(message = "Currency is required")
    Currency currency,
    WalletStatus status
) {
}
