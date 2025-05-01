// src/main/java/com/archproj/erp_backend/models/DigitalProduct.java
package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.ProductTypeEnum;

public class DigitalProduct extends Product {
    public DigitalProduct(String name, Double price) {
        super(name, price);
    }

    @Override
    public ProductTypeEnum getType() {
        return ProductTypeEnum.DIGITAL;
    }
}
