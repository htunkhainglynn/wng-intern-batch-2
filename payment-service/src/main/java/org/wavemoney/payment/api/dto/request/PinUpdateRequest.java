package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record PinUpdateRequest(
        @NotBlank(message = "phone is required")
        String phone,

        @NotBlank(message = "old pin is required")
        String oldPin,
        @NotBlank(message = "new pin is required")
        String newPin
) {
}
