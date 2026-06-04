package org.wavemoney.payment.api.service.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wavemoney.payment.api.dto.request.CashInRequest;
import org.wavemoney.payment.api.dto.response.TransactionResponse;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.entity.Wallet;
import org.wavemoney.payment.api.exception.BusinessLogicException;
import org.wavemoney.payment.api.repository.WalletRepository;
import org.wavemoney.payment.api.service.TransactionService;
import org.wavemoney.payment.api.service.WalletService;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    @Value("${transaction-amount.max}")
    private Double maxAmount;

    @Value("${transaction-amount.min}")
    private Double minAmount;

    @Value("${wallet-limit}")
    private Double walletLimit;

    private final WalletRepository walletRepository;
    private final WalletService walletService;

    @Override
    public TransactionResponse cashIn(CashInRequest request) {
        // TODO: amount limit check
        validateAmountLimit(request.amount());

        // TODO: insufficient balance
        validateInsufficientBalance(request);

        // TODO: user wallet limit
        validateUserWalletLimit(request);

        // TODO: subtract balance from sender wallet
        // TODO: add balance to receiver wallet
        // TODO: save transaction
        // TODO: send event to Kafka for notification
        // TODO: return transaction response

        return null;
    }

    private void validateUserWalletLimit(CashInRequest request) {
        String userPhoneNumber = request.from();
        WalletResponse wallet = walletService.getWalletByPhoneNumber(userPhoneNumber);
        if (walletLimit < wallet.balance() + request.amount()) {
            throw BusinessLogicException.business("WALLET_LIMIT_EXCEEDED", "Wallet limit exceeded");
        }
    }

    private void validateInsufficientBalance(CashInRequest request) {
        String userPhoneNumber = request.from();
        WalletResponse wallet = walletService.getWalletByPhoneNumber(userPhoneNumber);
        if (wallet.balance() < request.amount()) {
            throw BusinessLogicException.business("INSUFFICIENT_BALANCE", "Insufficient balance");
        }
    }

    private void validateAmountLimit(Double amount) {
        if (amount < minAmount) {
            throw BusinessLogicException.business("INVALID_AMOUNT", "Amount must be greater than " + minAmount);
        } else  if (amount > maxAmount) {
            throw BusinessLogicException.business("INVALID_AMOUNT", "Amount must be less than " + maxAmount);
        }
    }
}
