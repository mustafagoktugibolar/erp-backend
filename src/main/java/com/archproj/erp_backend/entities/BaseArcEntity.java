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

    @Column(name = "module_id")
    protected Long moduleId = -1L; // Default to -1 (System/Core)

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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
