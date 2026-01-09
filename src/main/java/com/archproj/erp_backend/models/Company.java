package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CompanyTypeEnum;

import java.util.HashMap;
import java.util.Map;

public class Company {
    private Long id;
    private String name;
    private String email;
    private Long moduleId;
    private CompanyTypeEnum type;
    private Map<String, Object> data = new HashMap<>();

    public Company() {
    }

    public Company(String name, String email, CompanyTypeEnum type) {
        this.name = name;
        this.email = email;
        this.type = type;
    }

    public Company(Long id, String name, String email, Long moduleId, CompanyTypeEnum type, Map<String, Object> data) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.moduleId = moduleId;
        this.type = type;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public CompanyTypeEnum getType() {
        return type;
    }

    public void setType(CompanyTypeEnum type) {
        this.type = type;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Company{id=" + id + ", name='" + name + "', email='" + email + "', moduleId=" + moduleId + "}";
    }
}
