package org.wavemoney.payment.config.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Service
public class JwtService {

    public static final String CLAIM_STATUS = "status";

    private final SecurityProperties props;
    private final SecretKey signingKey;

    public JwtService(SecurityProperties props) {
        this.props = props;
        this.signingKey = Keys.hmacShaKeyFor(props.jwt().secret().getBytes(StandardCharsets.UTF_8));
    }

    public String issue(String subject, String status) {
        Instant now = Instant.now();
        Instant exp = now.plusMillis(props.jwt().expirationMs());
        return Jwts.builder()
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(exp))
                .claims(Map.of(CLAIM_STATUS, status))
                .signWith(signingKey)
                .compact();
    }

    public Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long expirationMs() {
        return props.jwt().expirationMs();
    }
}
