package org.wavemoney.payment.api.dto.response;

import lombok.Builder;

@Builder
public record WalletResponse(
        String walletId,
        String phone,
        double balance,
        String currency,
        String status
) {

}
