package com.archproj.erp_backend.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.MappedSuperclass;

import java.util.HashMap;
import java.util.Map;

@MappedSuperclass
public abstract class BaseArcEntity {

    @Column(name = "data", columnDefinition = "TEXT")
    @Convert(converter = com.archproj.erp_backend.utils.JpaJsonConverter.class)
    protected Map<String, Object> data = new HashMap<>();

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public void setDynamic(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

    public Object getDynamic(String key) {
        if (data == null) {
            return null;
        }
        return data.get(key);
    }
}
