package org.wavemoney.payment.api.dto.response;

import org.wavemoney.payment.api.enums.*;

public record WalletResponse(String walletId, String phoneNumber, double balance, String currency, String status) {

}
