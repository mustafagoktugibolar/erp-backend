package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CustomerTypeEnum;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.ConstructorParameters;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    public abstract CustomerTypeEnum getType();

    public Customer(String name, String email) {}
}
