package org.wavemoney.payment.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record KYCFormRequest (
        @NotBlank(message = "name is required")
        String name,

        @NotBlank(message = "phone is required")
        @Pattern(regexp = "^[0-9+\\-\\s]{6,20}$", message = "phone format is invalid")
        String phone,

        @NotBlank(message = "NRC is required")
        String nrc,

        @Pattern(regexp = "^(?!.*([0-9])\\1{3}).*$", message = "pin must be 4 digits")
        @NotBlank(message = "pin is required")
        String pin,

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
) {}
