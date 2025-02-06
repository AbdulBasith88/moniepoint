package com.moniepoint.db.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "key_value")
public class KeyValue {
    @Id
    private String key;
    @Indexed
    private String value;
}
