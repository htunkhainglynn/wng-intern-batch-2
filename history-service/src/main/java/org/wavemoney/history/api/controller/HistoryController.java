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


}