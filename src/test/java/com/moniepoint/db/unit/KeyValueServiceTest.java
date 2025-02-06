package com.moniepoint.db.unit;

import com.moniepoint.db.model.KeyValue;
import com.moniepoint.db.service.KeyValueService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class KeyValueServiceTest {
    @Autowired
    private KeyValueService service;

    @Test
    public void testPutAndGet() {
        KeyValue keyValue = new KeyValue();
        keyValue.setKey("key1");
        keyValue.setValue("value1");
        service.put(keyValue);
        String value = service.get("key1");
        assertEquals("value1", value);
    }

    @Test
    public void testDelete() {
        KeyValue keyValue = new KeyValue();
        keyValue.setKey("key2");
        keyValue.setValue("value2");
        service.put(keyValue);
        service.delete("key2");
        String value = service.get("key2");
        assertNull(value);
    }

    @Test
    public void testBatchPut() {
        List<KeyValue> keysValues = new ArrayList<>();

        KeyValue keyValue3 = new KeyValue();
        keyValue3.setKey("key3");
        keyValue3.setValue("value3");
        keysValues.add(keyValue3);

        KeyValue keyValue4 = new KeyValue();
        keyValue4.setKey("key3");
        keyValue4.setValue("value3");
        keysValues.add(keyValue4);

        service.batchPut(keysValues);
        assertEquals("value3", service.get("key3"));
        assertEquals("value4", service.get("key4"));
    }

    @Test
    public void testReadKeyRange() {
        KeyValue keyValue = new KeyValue();
        keyValue.setKey("key5");
        keyValue.setValue("value5");
        service.put(keyValue);
        keyValue.setKey("key6");
        keyValue.setValue("value6");
        service.put(keyValue);
        List<String> range = service.readKeyRange("key2", "key6");
        assertTrue(range.contains("Key: key5, Value: value5"));
        assertTrue(range.contains("Key: key6, Value: value6"));
    }
}
