package org.wavemoney.payment.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.payment.api.dto.request.CashInRequest;
import org.wavemoney.payment.api.dto.request.TransactionRequest;
import org.wavemoney.payment.api.dto.response.ApiResponse;
import org.wavemoney.payment.api.dto.response.TransactionResponse;
import org.wavemoney.payment.api.service.TransactionService;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/cash-in")
    public ResponseEntity<ApiResponse<TransactionResponse>> cashIn(@Valid @RequestBody CashInRequest cashInRequest) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.cashIn(cashInRequest)));
    }

    @PostMapping("/adjustment")
    public ResponseEntity<ApiResponse<TransactionResponse>> adjustment(@Valid @RequestBody TransactionRequest transactionRequest) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.adjustment(transactionRequest)));
    }
}
