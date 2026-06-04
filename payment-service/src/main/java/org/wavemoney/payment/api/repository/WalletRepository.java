package org.wavemoney.payment.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.wavemoney.payment.api.dto.response.WalletStatusProjection;
import org.wavemoney.payment.api.entity.Wallet;

import java.util.Optional;

public interface WalletRepository extends MongoRepository<Wallet, String> {
	Optional<Wallet> findByPhoneNumber(String phoneNumber);

    @Query(
            value = "{ 'phoneNumber' : ?0 }",
            fields = "{ 'status' : 1}"
    )
    WalletStatusProjection getStatusByPhoneNumber(String phone);


}
