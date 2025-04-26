package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.ProductTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Product {
    private String name;
    private Double price;

    public abstract ProductTypeEnum getType();
}
