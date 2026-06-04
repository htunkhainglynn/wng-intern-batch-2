package org.wavemoney.payment.api.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.wavemoney.payment.api.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByPhoneOrNrc(String phone, String nrc);
    Optional<User> findByPhone(String phone);
    boolean existsByPhone(String phone);
    void deleteByPhone(String phone);
}
