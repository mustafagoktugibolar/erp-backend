package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CustomerTypeEnum;


public class CorporateCustomer extends Customer {
    public CorporateCustomer(String name, String email) {
        super(name, email);
    }
    public CorporateCustomer(Long id, String name, String email) {
        super(id, name, email);
    }
    @Override public CustomerTypeEnum getType() {
        return CustomerTypeEnum.CORPORATE;
    }
}

