package com.archproj.erp_backend.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue("corporate")
@Data
@NoArgsConstructor
public class CorporateCustomer extends Customer {
    public CorporateCustomer(String name, String email) {
        super(name, email);
    }

    public void displayInfo() {
        System.out.println("Corporate Customer: " + name + " - " + email);
    }
}
