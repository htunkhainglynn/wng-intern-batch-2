package org.wavemoney.history.api.service;

import org.wavemoney.history.api.dto.event.TransactionEvent;
import org.wavemoney.history.api.dto.request.HistoryRequest;
import org.wavemoney.history.api.entity.History;

import java.util.List;

public interface HistoryService {

    List<History> getAllHistory(HistoryRequest request);

    void handleHistoryEvent(TransactionEvent event);

    List<History> getByFrom(String from);
}
