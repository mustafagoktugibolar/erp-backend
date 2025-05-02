package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CustomerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String email;
    private CustomerTypeEnum type;

    public Customer(String name, String email) {
        this.name = name;
        this.email = email;
        this.type = getType();
    }

    public Customer(Long id, String name, String email) {
        this.id              = id;
        this.name            = name;
        this.email           = email;
        this.type = getType();
    }

    public CustomerTypeEnum getType(){
        return CustomerTypeEnum.INDIVIDUAL;
    }
}

