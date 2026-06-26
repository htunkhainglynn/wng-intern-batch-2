package org.wavemoney.payment.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponse {
    private String name;
    private String walletStatus;
    private String phone;
    private String nrc;
    private String level;
    private String address;
    private String dateOfBirth;
    private String gender;
    private String nationality;
    private String occupation;
    private String kycStatus;
}
