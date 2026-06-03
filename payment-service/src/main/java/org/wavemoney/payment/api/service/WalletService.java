package org.wavemoney.payment.api.service;

import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.WalletResponse;

public interface WalletService {

    WalletResponse create(WalletRequest request);

    WalletResponse getWalletByPhoneNumber(String phoneNumber);
}
