package org.wavemoney.history.api.service;

import org.wavemoney.history.api.dto.event.TransactionEvent;
import org.wavemoney.history.api.entity.History;

import java.util.List;

public interface HistoryService {

    List<History> getAllHistory(String phone);

    void handleHistoryEvent(TransactionEvent event);
}
