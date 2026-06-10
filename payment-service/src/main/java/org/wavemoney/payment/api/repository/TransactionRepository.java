package org.wavemoney.payment.api.repository;

import lombok.NonNull;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.wavemoney.payment.api.entity.Transaction;
import org.wavemoney.payment.api.entity.User;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends MongoRepository<@NonNull Transaction, @NonNull String> {

}
