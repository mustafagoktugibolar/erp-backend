package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CompanyTypeEnum;
import jakarta.persistence.*;

@Entity
@Table(name = "companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Enumerated(EnumType.STRING)
    private CompanyTypeEnum companyType;

    // Constructors, Getters, and Setters
}
