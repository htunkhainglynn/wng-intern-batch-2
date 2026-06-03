package org.wavemoney.payment.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.ApiResponse;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.service.WalletService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

	private final WalletService walletService;

	@PostMapping
	public ResponseEntity<ApiResponse<WalletResponse>> create(@Valid @RequestBody WalletRequest request) {
		WalletResponse created = walletService.create(request);
		return ResponseEntity
				.status(HttpStatus.CREATED)
				.body(ApiResponse.success(created, HttpStatus.CREATED.value(), "Wallet created"));
	}

//	@GetMapping("/{walletId}")
//	public ResponseEntity<ApiResponse<WalletResponse>> getById(@PathVariable String walletId) {
//		WalletResponse wallet = walletService.getById(walletId);
//		return ResponseEntity.ok(ApiResponse.success(wallet));
//	}

	@GetMapping("/{phoneNumber}")
	public ResponseEntity<ApiResponse<List<WalletResponse>>> getWalletByPhoneNumber(@PathVariable String phoneNumber) {
		List<WalletResponse> wallets = walletService.getWalletByPhoneNumber(phoneNumber);
		return ResponseEntity.ok(ApiResponse.success(wallets));
	}
}
