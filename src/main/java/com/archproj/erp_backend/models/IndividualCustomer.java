package com.archproj.erp_backend.models;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("individual")
public class IndividualCustomer extends Customer {
    public IndividualCustomer(String name, String email) {
        super(name, email);
    }
    public IndividualCustomer() {}

    public void displayInfo() {
        System.out.println("Individual Customer: " + name + " - " + email);
    }
}
