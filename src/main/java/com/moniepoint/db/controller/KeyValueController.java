package com.moniepoint.db.controller;

import com.moniepoint.db.model.KeyValue;
import com.moniepoint.db.service.KeyValueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/keyvalue")
public class KeyValueController {
    @Autowired
    private KeyValueService service;

    @PostMapping
    public void put(@RequestBody KeyValue keyValue) {
        service.put(keyValue);
        log.debug("Inserted record with key: %s and value: %s".formatted(keyValue.getValue(), keyValue.getValue()));
    }

    @GetMapping
    public String get(@RequestParam String key) {
        return service.get(key);
    }

    @DeleteMapping
    public void delete(@RequestParam String key) {
        log.debug("Deleting key: %s".formatted(key));
        service.delete(key);
    }

    @GetMapping("/range")
    public List<String> readKeyRange(@RequestParam String startKey, @RequestParam String endKey) {
        return service.readKeyRange(startKey, endKey);
    }

    @PostMapping(value = "/batch", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void batchPut(@RequestBody List<KeyValue> keysValues) {
        log.debug("Executing batch insert");
        service.batchPut(keysValues);
    }
}
