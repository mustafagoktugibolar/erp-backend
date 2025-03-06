package com.archproj.erp_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("individual")
@Data
@NoArgsConstructor
public class IndividualCustomer extends Customer {
    public IndividualCustomer(String name, String email) {
        super(name, email);
    }

    public void displayInfo() {
        System.out.println("Individual Customer: " + name + " - " + email);
    }
}
