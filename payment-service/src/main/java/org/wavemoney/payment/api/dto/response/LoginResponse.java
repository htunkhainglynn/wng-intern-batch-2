package org.wavemoney.payment.api.dto.response;

import lombok.Builder;

@Builder
public record LoginResponse(
        UserResponse user,
        String accessToken,
        String tokenType,
        long expiresInMs
) {}
