package org.wavemoney.payment.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.wavemoney.payment.api.entity.Wallet;

public interface WalletRepository extends MongoRepository<Wallet, String> {
	java.util.List<Wallet> findByPhoneNumber(String phoneNumber);
}
