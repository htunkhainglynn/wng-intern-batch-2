package org.wavemoney.notification.api.entity;

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
@Document(collection = "Notification")
@CompoundIndexes({
        @CompoundIndex(
                name = "uniq_transactionId_recipient",
                def = "{'transactionId': 1, 'recipient': 1}",
                unique = true
        )
})
public class Notification {

    @Id
    private String id;
    private String transactionId;
    private String recipient;
    private String type;
    private String message;
    private String status;
    private Instant createdAt;
}
