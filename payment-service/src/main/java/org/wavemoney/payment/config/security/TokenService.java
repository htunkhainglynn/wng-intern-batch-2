package org.wavemoney.payment.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate;
    private static final String TOKEN_STATUS_PREFIX = "token:status:";

    public void markTokenAsActive(String phone, long expirationMs) {
        String key = TOKEN_STATUS_PREFIX + phone;
        redisTemplate.opsForValue().set(key, "ACTIVE", expirationMs, TimeUnit.MILLISECONDS);
    }

    public void markTokenAsLoggedOut(String phone) {
        String key = TOKEN_STATUS_PREFIX + phone;
        redisTemplate.opsForValue().set(key, "LOGGED_OUT");
    }

    public String getTokenStatus(String phone) {
        String key = TOKEN_STATUS_PREFIX + phone;
        Object status = redisTemplate.opsForValue().get(key);
        return status != null ? status.toString() : "UNKNOWN";
    }

    public boolean isTokenActive(String phone) {
        return "ACTIVE".equals(getTokenStatus(phone));
    }
}
