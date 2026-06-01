package org.wavemoney.payment.api.repository;

import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.wavemoney.payment.api.entity.Wallet;

public interface WalletRepository extends MongoRepository<@NonNull Wallet, @NonNull String> {
}
