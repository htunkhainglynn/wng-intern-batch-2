package org.wavemoney.notification.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.wavemoney.notification.api.entity.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends MongoRepository<Notification, String> {

    List<Notification> findByRecipient(String recipient);

    List<Notification> findByTransactionId(String transactionId);
}
