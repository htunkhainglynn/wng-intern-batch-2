package org.wavemoney.payment.api.service;

import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.WalletResponse;

import java.util.List;

public interface WalletService {

    WalletResponse create(WalletRequest request);

    WalletResponse getWalletByPhoneNumber(String phoneNumber);

    void deleteWalletByPhone(String phone);

    List<WalletResponse> getAllWallets();
}
