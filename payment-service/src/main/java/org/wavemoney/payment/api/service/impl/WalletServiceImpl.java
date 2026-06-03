package org.wavemoney.payment.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.entity.User;
import org.wavemoney.payment.api.entity.Wallet;
import org.wavemoney.payment.api.exception.BusinessLogicException;
import org.wavemoney.payment.api.enums.WalletStatus;
import org.wavemoney.payment.api.repository.UserRepository;
import org.wavemoney.payment.api.repository.WalletRepository;
import org.wavemoney.payment.api.service.WalletService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    @Override
    public WalletResponse create(WalletRequest request) {
        if (request.phoneNumber() == null || request.phoneNumber().isBlank()) {
            throw BusinessLogicException.validation("PHONE_NUMBER_REQUIRED", "Phone number is required");
        }

        User user = userRepository.findByPhone(request.phoneNumber())
                .orElseThrow(() -> BusinessLogicException.notFound("USER_NOT_FOUND", "User with phone number " + request.phoneNumber() + " not found"));

        String id = (request.walletId() != null && !request.walletId().isBlank()) ? request.walletId() : UUID.randomUUID().toString();

        Wallet wallet = Wallet.builder()
                .walletId(id)
                .phoneNumber(user.getPhone())
                .balance(request.balance())
                .currency(request.currency())
                .status(request.status() != null ? request.status() : WalletStatus.ACTIVE)
                .build();

        Wallet saved = walletRepository.save(wallet);
        return toResponse(saved);
    }

    private WalletResponse toResponse(Wallet saved) {
        return new WalletResponse(saved.getWalletId(), saved.getPhoneNumber(), saved.getBalance(), saved.getCurrency(), saved.getStatus());
    }

    @Override
    public WalletResponse getById(String walletId) {
        Wallet saved = walletRepository.findById(walletId)
                .orElseThrow(() -> BusinessLogicException.notFound("WALLET_NOT_FOUND", "Wallet with id " + walletId + " not found"));
        return toResponse(saved);
    }

    @Override
    public List<WalletResponse> getWalletByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return List.of();
        return walletRepository.findByPhoneNumber(phoneNumber).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}
