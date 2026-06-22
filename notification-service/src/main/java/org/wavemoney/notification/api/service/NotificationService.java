package org.wavemoney.notification.api.service;

import org.wavemoney.notification.api.dto.event.TransactionEvent;
import org.wavemoney.notification.api.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<Notification> handleTransferEvent(TransactionEvent event);

    Optional<Notification> handleCashinEvent(TransactionEvent event);

    // Return notifications for a given recipient phone number
    List<Notification> getNotifications(String phone);


}
