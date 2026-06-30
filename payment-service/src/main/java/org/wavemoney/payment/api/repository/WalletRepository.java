package org.wavemoney.payment.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.wavemoney.payment.api.dto.response.WalletStatusProjection;
import org.wavemoney.payment.api.entity.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository extends MongoRepository<Wallet, String> {
	Optional<Wallet> findByPhone(String phone);

    List<Wallet> findByPhoneIn(List<String> phones);

    //fetch status only for user
    @Query(
            value = "{ 'phone' : ?0 }",
            fields = "{ 'status' : 1}"
    )
    WalletStatusProjection getStatusByPhone(String phone);
}
