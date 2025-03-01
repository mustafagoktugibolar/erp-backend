package com.archproj.erp_backend.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("corporate")
public class CorporateCustomer extends Customer {
    public CorporateCustomer(String name, String email) {
        super(name, email);
    }
    public CorporateCustomer() {}

    public void displayInfo() {
        System.out.println("Corporate Customer: " + name + " - " + email);
    }
}
