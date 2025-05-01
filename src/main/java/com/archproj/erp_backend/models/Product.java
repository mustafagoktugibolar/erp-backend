// src/main/java/com/archproj/erp_backend/models/Product.java
package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Product {
    private Long id;
    private String name;
    private Double price;


    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public abstract ProductTypeEnum getType();

}
