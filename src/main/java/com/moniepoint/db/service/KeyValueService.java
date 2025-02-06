package com.moniepoint.db.service;

import com.moniepoint.db.model.KeyValue;
import com.moniepoint.db.repository.KeyValueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.List;

@Service
public class KeyValueService {
    private final Jedis redis;
    @Autowired
    private KeyValueRepository repository;

    public KeyValueService() {
        this.redis = new Jedis("localhost", 6379);
    }

    public void put(KeyValue keyValue) {
        // Save to MongoDB
        repository.save(keyValue);

        // Save to Redis
        redis.set(keyValue.getKey(), keyValue.getValue());
    }

    public String get(String key) {
        // Check in Redis
        String value = redis.get(key);
        if (value != null) {
            return value;
        }

        // Check in MongoDB
        KeyValue keyValue = repository.findById(key).orElse(null);
        if (keyValue != null) {
            redis.set(key, keyValue.getValue()); // Cache the value
            return keyValue.getValue();
        }

        return null;
    }

    public void delete(String key) {
        // Delete from MongoDB
        repository.deleteById(key);

        // Delete from Redis
        redis.del(key);
    }

    public List<String> readKeyRange(String startKey, String endKey) {
        List<KeyValue> keysValues = repository.findByKeyBetween(startKey, endKey);
        List<String> results = new ArrayList<>();

        for (KeyValue keyValue : keysValues) {
            redis.set(keyValue.getKey(), keyValue.getValue()); // Cache the range
            results.add("Key: " + keyValue.getKey() + ", Value: " + keyValue.getValue());
        }

        return results;
    }

    public void batchPut(List<KeyValue> keysValues) {
        // Save to MongoDB
        repository.saveAll(keysValues);

        // Save to Redis
        for (KeyValue keyValue : keysValues) {
            redis.set(keyValue.getKey(), keyValue.getValue()); // Cache each key-value
        }
    }
}
