package com.moniepoint.db.repository;

import com.moniepoint.db.model.KeyValue;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface KeyValueRepository extends MongoRepository<KeyValue, String> {
    @Query("{ 'key': { $gte: ?0, $lte: ?1 } }")
    List<KeyValue> findByKeyBetween(String startKey, String endKey);
}
