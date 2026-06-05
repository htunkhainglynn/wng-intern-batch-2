package org.wavemoney.payment.api.service;

import org.wavemoney.payment.api.dto.request.CashInRequest;
import org.wavemoney.payment.api.dto.request.TransactionRequest;
import org.wavemoney.payment.api.dto.response.TransactionResponse;

public interface TransactionService {
    TransactionResponse cashIn(CashInRequest request);
    TransactionResponse adjustment(TransactionRequest request);
}
