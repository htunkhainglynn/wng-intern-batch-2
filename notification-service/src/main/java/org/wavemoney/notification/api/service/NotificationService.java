package org.wavemoney.notification.api.service;

import org.wavemoney.notification.api.dto.event.TransactionEvent;
import org.wavemoney.notification.api.entity.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

    List<Notification> handleCashInEvent(TransactionEvent event);

    Optional<Notification> handleAdjustmentEvent(TransactionEvent event);
}
