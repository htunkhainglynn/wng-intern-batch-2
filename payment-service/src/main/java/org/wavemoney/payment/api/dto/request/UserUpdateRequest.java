package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserUpdateRequest(
        @NotBlank(message = "name is required")
        String name
) {
}
