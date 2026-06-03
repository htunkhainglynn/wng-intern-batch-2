package org.wavemoney.payment.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.wavemoney.payment.api.entity.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByPhoneOrNrc(String phone, String nrc);
    Optional<User> findByPhone(String phone);
    Optional<User> findByNrc(String nrc);

    String nrc(String nrc);
}
