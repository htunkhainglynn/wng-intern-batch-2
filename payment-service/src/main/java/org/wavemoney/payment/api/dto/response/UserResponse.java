package org.wavemoney.payment.api.dto.response;

public record UserResponse(String id, String name, String phone, String password, String nrc) {
}
