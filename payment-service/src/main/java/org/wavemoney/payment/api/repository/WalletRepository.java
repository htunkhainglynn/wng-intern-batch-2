package org.wavemoney.payment.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.wavemoney.payment.api.entity.Wallet;

import java.util.Optional;

public interface WalletRepository extends MongoRepository<Wallet, String> {
	Optional<Wallet> findByPhoneNumber(String phoneNumber);
}
