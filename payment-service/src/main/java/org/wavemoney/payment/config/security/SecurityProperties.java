package org.wavemoney.payment.config.security;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "app.security")
public record SecurityProperties(
        Jwt jwt,
        List<String> publicPaths
) {
    public record Jwt(
            String secret,
            long expirationMs
    ) {}
}
