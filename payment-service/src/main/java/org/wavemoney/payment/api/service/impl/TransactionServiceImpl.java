package org.wavemoney.payment.api.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wavemoney.payment.api.dto.request.CashInRequest;
import org.wavemoney.payment.api.dto.request.TransactionRequest;
import org.wavemoney.payment.api.dto.response.TransactionResponse;
import org.wavemoney.payment.api.dto.response.WalletResponse;
import org.wavemoney.payment.api.entity.Transaction;
import org.wavemoney.payment.api.entity.Wallet;
import org.wavemoney.payment.api.enums.TransactionStatus;
import org.wavemoney.payment.api.enums.TransactionType;
import org.wavemoney.payment.api.exception.BusinessLogicException;
import org.wavemoney.payment.api.repository.TransactionRepository;
import org.wavemoney.payment.api.repository.WalletRepository;
import org.wavemoney.payment.api.service.TransactionService;
import org.wavemoney.payment.api.service.WalletService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    //private final KafkaTemplate kafkaTemplate;

    @Value("${transaction-amount.max}")
    private Double maxAmount;

    @Value("${transaction-amount.min}")
    private Double minAmount;

    @Value("${wallet-limit}")
    private Double walletLimit;

    private final WalletRepository walletRepository;
    private final WalletService walletService;

    @Override
    @Transactional
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
        return saveTransaction(request);
    }

    @Override
    public TransactionResponse adjustment(TransactionRequest request) {
        String userPhone = request.to();
        WalletResponse wallet = walletService.getWalletByPhone(userPhone);
        Double newBalance = wallet.balance() + request.amount();
        if (walletLimit < newBalance) {
            throw BusinessLogicException.business("WALLET_LIMIT_EXCEEDED", "Wallet limit exceeded");
        }
        updateBalanceByPhone(userPhone, newBalance);
        return saveAdjustmentTransaction(request);
    }


    private void validateDifferentWallet(CashInRequest request) {
        String from = request.from();
        String to = request.to();
        if(from.equals(to)) {
            throw BusinessLogicException.business("INVALID_REQUEST", "Sender and receiver wallets cannot be the same");
        }
    }

    private void validateUserWalletLimit(CashInRequest request) {
        String userPhone = request.from();
        WalletResponse wallet = walletService.getWalletByPhone(userPhone);
        if (walletLimit < wallet.balance() + request.amount()) {
            throw BusinessLogicException.business("WALLET_LIMIT_EXCEEDED", "Wallet limit exceeded");
        }
    }

    private void validateInsufficientBalance(CashInRequest request) {
        String userPhone = request.from();
        WalletResponse wallet = walletService.getWalletByPhone(userPhone);
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
        String userPhone = request.from();
        WalletResponse wallet = walletService.getWalletByPhone(userPhone);
        Double newBalance = wallet.balance() - request.amount();
        updateBalanceByPhone(userPhone, newBalance);
    }

    private void addBalanceToReceiverWallet(CashInRequest request){
        String userPhone = request.to();
        WalletResponse wallet = walletService.getWalletByPhone(userPhone);
        Double newBalance = wallet.balance() + request.amount();
        updateBalanceByPhone(userPhone, newBalance);
        throw BusinessLogicException.business("TEST_ATOMIC", "Testing atomic.");
    }

    private void updateBalanceByPhone(String phone, Double balance){
        Wallet wallet = walletRepository.findByPhone(phone)
                .orElseThrow(() -> BusinessLogicException.notFound("WALLET_NOT_FOUND", "Wallet with phone " + phone + " not found"));

        wallet.setBalance(balance);
        walletRepository.save(wallet);
    }

    private TransactionResponse saveTransaction(CashInRequest request){
        Transaction transaction = Transaction.builder()
                .from(request.from())
                .to(request.to())
                .amount(request.amount())
                .status(TransactionStatus.SUCCESS.name())
                .transactionType(TransactionType.CASH_IN.name())
                .transactionTime(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        // send event to kafka
       // kafkaTemplate.send("transaction-events", saved);

        return toResponse(saved);
    }

    private TransactionResponse saveAdjustmentTransaction(TransactionRequest request){
        Transaction transaction = Transaction.builder()
                .from("SYSTEM")
                .to(request.to())
                .amount(request.amount())
                .status(TransactionStatus.SUCCESS.name())
                .transactionType(TransactionType.ADJUSTMENT.name())
                .transactionTime(LocalDateTime.now())
                .build();

        Transaction saved = transactionRepository.save(transaction);

        // send event to kafka
        // kafkaTemplate.send("transaction-events", saved);

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

}
