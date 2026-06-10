package org.wavemoney.history.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.history.api.entity.History;
import org.wavemoney.history.api.service.HistoryService;
import org.wavemoney.history.api.dto.response.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/history")
@RequiredArgsConstructor
public class HistoryController {

    private final HistoryService historyService;

    @GetMapping("/{phone}")
    public ResponseEntity <List<History>> getAllHistory(@PathVariable String phone) {
        List<History> history = historyService.getAllHistory(phone);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/sent/{phone}")
    public ResponseEntity<List<History>> getSentHistory(@PathVariable String phone) {
        List<History> history = historyService.getSentHistory(phone);
        return ResponseEntity.ok(history);
    }

    @GetMapping("/received/{phone}")
    public ResponseEntity<List<History>> getReceivedHistory(@PathVariable String phone) {
        List<History> history = historyService.getReceivedHistory(phone);
        return ResponseEntity.ok(history);
    }


}