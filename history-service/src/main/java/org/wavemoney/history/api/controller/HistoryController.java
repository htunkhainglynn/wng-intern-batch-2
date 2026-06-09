package org.wavemoney.history.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.history.api.dto.request.HistoryRequest;
import org.wavemoney.history.api.entity.History;
import org.wavemoney.history.api.service.HistoryService;
import org.wavemoney.history.api.dto.response.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping()
    public ResponseEntity <List<History>> getAllHistory(@RequestBody HistoryRequest historyRequest) {
        List<History> history = historyService.getAllHistory(historyRequest);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/send-money")
    public ResponseEntity <List<History>> getByFrom(@RequestBody String from) {
        List<History> history = historyService.getByFrom(from);
        return ResponseEntity.ok(history);
    }
}