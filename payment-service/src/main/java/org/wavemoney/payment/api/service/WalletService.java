package org.wavemoney.payment.api.service;

import org.wavemoney.payment.api.dto.request.WalletRequest;
import org.wavemoney.payment.api.dto.response.WalletResponse;

import java.util.List;
import java.util.Map;

public interface WalletService {

    WalletResponse create(WalletRequest request);

    WalletResponse getWalletByPhone(String phone);

    void deleteWalletByPhone(String phone);

    List<WalletResponse> getAllWallets();

    String getWalletStatusByPhone(String phone);

    Map<String, String> getWalletStatusesByPhones(List<String> phones);
}
