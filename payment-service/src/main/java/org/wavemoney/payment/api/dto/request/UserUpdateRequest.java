package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(

        @NotBlank(message = "Address is required")
        String address,

        @NotBlank(message = "Date of Birth is required")
        String dateOfBirth,

        @NotBlank(message = "Gender is required")
        String gender,

        @NotBlank(message = "Nationality is required")
        String nationality,

        @NotBlank(message = "Occupation is required")
        String occupation
) {
}
