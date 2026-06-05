package org.wavemoney.payment.api.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Builder
@Data
public class Transaction {
    @Id
    String id;
    String from; // to fetch from JWT tokens
    String to;
    String amount;
    String TransactionType;
    String Status;
    String transactionType;
    String transactionTime;

}
