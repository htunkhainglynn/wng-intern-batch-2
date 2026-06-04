package org.wavemoney.payment.api.dto.response;

import lombok.Builder;

@Builder
public record WalletResponse(
        String walletId,
        String phoneNumber,
        double balance,
        String currency,
        String status
) {

}
