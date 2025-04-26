package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.ProductTypeEnum;

public class PhysicalProduct extends Product {

    public PhysicalProduct(String name, Double price) {
        super(name, price);
    }

    @Override
    public ProductTypeEnum getType() {
        return ProductTypeEnum.PHYSICAL;
    }
}
