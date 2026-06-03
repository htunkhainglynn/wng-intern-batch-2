package org.wavemoney.payment.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.wavemoney.payment.api.dto.response.ApiResponse;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.service.WalletService;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

	private final WalletService walletService;

	@GetMapping("/{phoneNumber}")
	public ResponseEntity<ApiResponse<WalletResponse>> getWalletByPhoneNumber(@PathVariable String phoneNumber) {
		WalletResponse wallet = walletService.getWalletByPhoneNumber(phoneNumber);
		return ResponseEntity.ok(ApiResponse.success(wallet));
	}


}
