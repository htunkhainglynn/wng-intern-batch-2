package org.wavemoney.notification.api.service;

import org.wavemoney.notification.api.dto.event.TransactionEvent;
import org.wavemoney.notification.api.entity.Notification;

public interface NotificationService {

    Notification handleTransactionEvent(TransactionEvent event);
}
