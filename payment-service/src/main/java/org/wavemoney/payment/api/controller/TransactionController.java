package org.wavemoney.payment.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.payment.api.dto.request.CashoutRequest;
import org.wavemoney.payment.api.dto.request.TransferRequest;
import org.wavemoney.payment.api.dto.request.CashinRequest;
import org.wavemoney.payment.api.dto.response.ApiResponse;
import org.wavemoney.payment.api.dto.response.TransactionResponse;
import org.wavemoney.payment.api.service.TransactionService;
import org.wavemoney.payment.api.service.UserService;
import org.wavemoney.payment.api.dto.request.LoginRequest;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;
    private final UserService userService;

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<TransactionResponse>> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.transfer(transferRequest)));
    }

    @PostMapping("/cashin")
    public ResponseEntity<ApiResponse<TransactionResponse>> cashin(@Valid @RequestBody CashinRequest cashinRequest) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.cashin(cashinRequest)));
    }

    @PostMapping("/cashout")
    public ResponseEntity<ApiResponse<TransactionResponse>> cashout(@Valid @RequestBody CashoutRequest cashoutRequest) {
        return ResponseEntity.ok(ApiResponse.success(transactionService.cashout(cashoutRequest)));
    }

    @PostMapping("/verify-pin")
    public ResponseEntity<ApiResponse<String>> verifyPin(@RequestBody LoginRequest request) {
        userService.verifyPin(request.phone(), request.pin());
        return ResponseEntity.ok(ApiResponse.success("Pin verified"));
    }

    //history
    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<TransactionResponse>>> transactionHistory(@Valid @RequestBody String phone){
        List<TransactionResponse> transactions = transactionService.getAllTransactions();
        return ResponseEntity.ok(ApiResponse.success(transactions));
    }
}
