package org.wavemoney.history.api.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.wavemoney.history.api.dto.event.TransactionEvent;
import org.wavemoney.history.api.dto.request.HistoryRequest;
import org.wavemoney.history.api.entity.History;
import org.wavemoney.history.api.repository.HistoryRepository;
import org.wavemoney.history.api.service.HistoryService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    //private static final String SYSTEM_SENDER = "SYSTEM";

    private final HistoryRepository historyRepository;

    @Override
    public List<History> getAllHistory(String phone) {
        return historyRepository.findHistoryByFromOrTo(phone, phone);
    }

    @Override
    public List<History> getSentHistory(String phone) {
        return historyRepository.findHistoryByFrom(phone);
    }

    @Override
    public List<History> getReceivedHistory(String phone) {
        return historyRepository.findHistoryByTo(phone);
    }

    @Override
    public void handleHistoryEvent(TransactionEvent event) {
        List<History> saved = new ArrayList<>();
        saveIfAbsent(buildSentHistory(event)).ifPresent(saved::add);
        log.debug("Saved history for transactionId={}: {}", event.transactionId(), saved);
    }

    @Override
    public List<History> getByFrom(String from) {
        return historyRepository.findHistoryByFrom(from);
    }

    private Optional<History> saveIfAbsent(History history) {
        try {
            History persisted = historyRepository.save(history);
            log.debug("Saved history id={} for transactionId={} recipient={}",
                    persisted.getId(), persisted.getTransactionId(), persisted.getFrom());
            return Optional.of(persisted);
        } catch (DuplicateKeyException ex) {
            log.info("History already exists for transactionId={} recipient={}; skipping",
                    history.getTransactionId(), history.getFrom());
            return Optional.empty();
        }
    }

    private History buildSentHistory(TransactionEvent event) {
        return History.builder()
                .transactionId(event.transactionId())
                .from(event.from())
                .to(event.to())
                .amount(event.amount())
                .type(event.transactionType())
                .timestamp(Instant.now())
                .status(event.status())
                .build();
    }

}
