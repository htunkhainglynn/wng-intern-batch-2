package org.wavemoney.payment.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Transaction")
public class Transaction {

    @Id
    private String transactionId;
    private String from;
    private String to;
    private Double amount;
    private String status;
    private String transactionType;
    private String transactionTime;
}
