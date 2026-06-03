package org.wavemoney.payment.api.dto.response;

import org.wavemoney.payment.api.enums.*;

public record WalletResponse(String walletId, String userId, double balance, Currency currency, WalletStatus status) {

}
