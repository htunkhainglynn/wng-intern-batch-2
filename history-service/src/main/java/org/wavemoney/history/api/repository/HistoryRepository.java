package org.wavemoney.history.api.repository;

import com.mongodb.client.MongoIterable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.wavemoney.history.api.dto.response.HistoryResponse;
import org.wavemoney.history.api.entity.History;

import java.util.List;

@Repository
public interface HistoryRepository extends MongoRepository<History, String> {

    List<History> findHistoryByFromOrTo(String from, String to);

    List<History> findHistoryByFrom(String phone);

    List<History> findHistoryByTo(String phone);

}
