package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CompanyTypeEnum;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private CompanyTypeEnum type;

    private Map<String, Object> data = new HashMap<>();

    public Company(String name, String email, CompanyTypeEnum companyType) {
        this.name = name;
        this.email = email;
        this.type = companyType;
    }

    public CompanyTypeEnum getType() {
        return this.type;
    }
}
