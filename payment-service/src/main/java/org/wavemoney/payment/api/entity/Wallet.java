package org.wavemoney.payment.api.entity;

// removed JPA imports - using MongoDB documents instead
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "wallet")
public class Wallet {

    @Id
    private String walletId;
    private String phone;
    private double balance;
    private String currency;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}

