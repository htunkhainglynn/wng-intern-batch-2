package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "password is required")
        String password
) {
}
