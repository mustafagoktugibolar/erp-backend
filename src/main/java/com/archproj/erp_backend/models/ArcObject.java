package com.archproj.erp_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArcObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arc_object_id;
    private Long moduleId;
    @JsonProperty("data")
    private Map<String, Object> data = new HashMap<>();

    public Object get(String key) {
        return data.get(key);
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }
}
