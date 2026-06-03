package org.wavemoney.payment.api.dto.response;

public record WalletResponse(String walletId, String phoneNumber, double balance, String currency, String status) {

}
