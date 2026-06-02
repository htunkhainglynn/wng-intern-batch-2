package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserRequest(
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "email is required")
        @Email(message = "email must be valid")
        String email,

        @NotBlank(message = "phone is required")
        @Pattern(regexp = "^[0-9+\\-\\s]{6,20}$", message = "phone format is invalid")
        String phone
) {
}
