package com.archproj.erp_backend.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.HashMap;
import java.util.Map;

public class ArcObject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long arc_object_id;
    private Long moduleId;
    private String objectType = "ARC_OBJECT"; // Default type
    @JsonProperty("data")
    private Map<String, Object> data = new HashMap<>();

    public ArcObject() {
    }

    public ArcObject(Long arc_object_id, Long moduleId, String objectType, Map<String, Object> data) {
        this.arc_object_id = arc_object_id;
        this.moduleId = moduleId;
        this.objectType = objectType;
        this.data = data;
    }

    public Long getArc_object_id() {
        return arc_object_id;
    }

    public void setArc_object_id(Long arc_object_id) {
        this.arc_object_id = arc_object_id;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Object get(String key) {
        return data.get(key);
    }

    public void set(String key, Object value) {
        data.put(key, value);
    }
}
