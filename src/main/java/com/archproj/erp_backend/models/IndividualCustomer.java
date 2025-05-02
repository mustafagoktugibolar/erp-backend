package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CustomerTypeEnum;

public class IndividualCustomer extends Customer {
    public IndividualCustomer(String name, String email) {
        super(name, email);
    }
    public IndividualCustomer(Long id, String name, String email) {
        super(id, name, email);
    }
    @Override public CustomerTypeEnum getType() {
        return CustomerTypeEnum.INDIVIDUAL;
    }
}
