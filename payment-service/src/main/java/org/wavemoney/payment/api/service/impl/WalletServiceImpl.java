package org.wavemoney.payment.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.entity.Wallet;
import org.wavemoney.payment.api.enums.Currency;
import org.wavemoney.payment.api.exception.BusinessLogicException;
import org.wavemoney.payment.api.enums.WalletStatus;
import org.wavemoney.payment.api.repository.WalletRepository;
import org.wavemoney.payment.api.service.WalletService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletResponse create(WalletRequest request) {

        String id = (request.walletId() != null && !request.walletId().isBlank()) ? request.walletId() : UUID.randomUUID().toString();

        Wallet wallet = Wallet.builder()
                .walletId(id)
                .phoneNumber(request.phoneNumber())
                .balance(request.balance())
                .currency(Currency.MMK.name())
                .status(WalletStatus.ACTIVE.name())
                .build();

        Wallet saved = walletRepository.save(wallet);
        return toResponse(saved);
    }

    private WalletResponse toResponse(Wallet saved) {
        return new WalletResponse(saved.getWalletId(), saved.getPhoneNumber(), saved.getBalance(), saved.getCurrency(), saved.getStatus());
    }

    private List<WalletResponse> toResponse(List<Wallet> wallets) {
        return wallets
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WalletResponse getWalletByPhoneNumber(String phoneNumber) {
        Wallet wallet = walletRepository.findByPhoneNumber(phoneNumber).orElseThrow(() -> BusinessLogicException.notFound("WALLET_NOT_FOUND", "Wallet with phone number " + phoneNumber + " not found"));
        return toResponse(wallet);
    }

    @Override
    public List<WalletResponse> getAllWallets() {
        List<Wallet> wallets = walletRepository.findAll();
        return toResponse(wallets);
    }

    @Override
    public String getWalletStatusByPhone(String phone) {
        return walletRepository.getStatusByPhoneNumber(phone).getStatus().orElseThrow(() -> BusinessLogicException.notFound("WALLET_NOT_FOUND", "Wallet with phone number " + phone + " not found"));
    }

    @Override
    public void deleteWalletByPhone(String phone) {
        Wallet wallet = walletRepository.findByPhoneNumber(phone).orElseThrow(() -> BusinessLogicException.notFound("WALLET_NOT_FOUND", "Wallet with phone number " + phone + " not found"));
        walletRepository.delete(wallet);
    }

}
