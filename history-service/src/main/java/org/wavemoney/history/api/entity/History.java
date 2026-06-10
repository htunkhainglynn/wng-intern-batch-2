package org.wavemoney.history.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "History")
@CompoundIndexes({
        @CompoundIndex(
                name = "uniq_transactionId_recipient",
                def = "{'transactionId': 1, 'recipient': 1}",
                unique = true
        )
})
public class History {

    @Id
    private String id;
    private String transactionId;
    private String from;
    private String to;
    private String type;
    private double amount;
    // private String message;
    private String status;
    private Instant timestamp;
}
