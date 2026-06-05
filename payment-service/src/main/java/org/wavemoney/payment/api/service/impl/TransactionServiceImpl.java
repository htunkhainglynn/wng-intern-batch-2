package org.wavemoney.payment.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.wavemoney.payment.api.dto.request.CashInRequest;
import org.wavemoney.payment.api.dto.response.TransactionResponse;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.entity.Transaction;
import org.wavemoney.payment.api.entity.Wallet;
import org.wavemoney.payment.api.exception.BusinessLogicException;
import org.wavemoney.payment.api.repository.TransactionRepository;
import org.wavemoney.payment.api.repository.WalletRepository;
import org.wavemoney.payment.api.service.TransactionService;
import org.wavemoney.payment.api.service.WalletService;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
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

        validateDifferentWallet(request);
        // TODO: amount limit check
        validateAmountLimit(request.amount());

        // TODO: insufficient balance
        validateInsufficientBalance(request);

        // TODO: user wallet limit
        validateUserWalletLimit(request);

        // TODO: subtract balance from sender wallet
        subtractSenderBalance(request);
        // TODO: add balance to receiver wallet
        addBalanceToReceiverWallet(request);
        // TODO: save transaction and return response
        return SaveTransaction(request);
    }

    private void validateDifferentWallet(CashInRequest request) {
        String from = request.from();
        String to = request.to();
        if(from.equals(to)) {
            throw BusinessLogicException.business("INVALID_REQUEST", "Sender and receiver wallets cannot be the same");
        }
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

    private void subtractSenderBalance(CashInRequest request){
        String userPhoneNumber = request.from();
        WalletResponse wallet = walletService.getWalletByPhoneNumber(userPhoneNumber);
        Double newBalance = wallet.balance() - request.amount();
        updateBalanceByPhoneNumber(userPhoneNumber, newBalance);
    }

    private void addBalanceToReceiverWallet(CashInRequest request){
        String userPhoneNumber = request.to();
        WalletResponse wallet = walletService.getWalletByPhoneNumber(userPhoneNumber);
        Double newBalance = wallet.balance() + request.amount();
        updateBalanceByPhoneNumber(userPhoneNumber, newBalance);
    }

    private void updateBalanceByPhoneNumber(String phone, Double balance){
        Wallet wallet = walletRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> BusinessLogicException.notFound("WALLET_NOT_FOUND", "Wallet with phone " + phone + " not found"));

        wallet.setBalance(balance);
        wallet.setStatus("SUCCESS");
        Wallet saved = walletRepository.save(wallet);
        toResponse(saved);
    }

    private TransactionResponse SaveTransaction(CashInRequest request){
        Transaction transaction = Transaction.builder()
                .from(request.from())
                .to(request.to())
                .amount(request.amount())
                .build();

        Transaction saved = transactionRepository.save(transaction);
        return toResponse(saved);
    }

    private TransactionResponse toResponse(Transaction transaction) {
        return TransactionResponse.builder().transactionId(transaction.getTransactionId())
                .from(transaction.getFrom())
                .to(transaction.getTo())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .transactionType(transaction.getTransactionType())
                .transactionTime(transaction.getTransactionTime())
                .build();
    }

    private WalletResponse toResponse(Wallet wallet) {
        return WalletResponse.builder()
                .phoneNumber(wallet.getPhoneNumber())
                .balance(wallet.getBalance())
                .status(wallet.getStatus())
                .build();
    }
}
