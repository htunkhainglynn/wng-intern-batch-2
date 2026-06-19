package org.wavemoney.payment.api.service;

import jakarta.validation.Valid;
import org.wavemoney.payment.api.dto.request.CashoutRequest;
import org.wavemoney.payment.api.dto.request.TransferRequest;
import org.wavemoney.payment.api.dto.request.CashinRequest;
import org.wavemoney.payment.api.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    TransactionResponse transfer(TransferRequest request);
    TransactionResponse cashin(CashinRequest request);
    List<TransactionResponse> getAllTransactions();

    TransactionResponse cashout(CashoutRequest cashoutRequest);
}
