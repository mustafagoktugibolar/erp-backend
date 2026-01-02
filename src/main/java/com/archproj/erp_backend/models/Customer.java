package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CustomerTypeEnum;

public class Customer {
    private Long id;
    private String name;
    private String email;
    private CustomerTypeEnum type;

    public Customer() {
    }

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.type = CustomerTypeEnum.INDIVIDUAL;
    }

    public Customer(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = CustomerTypeEnum.INDIVIDUAL;
    }

    public Customer(Long id, String name, String email, CustomerTypeEnum type) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.type = type;
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

    public CustomerTypeEnum getType() {
        return type;
    }

    public void setType(CustomerTypeEnum type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", type=" + type +
                '}';
    }
}
