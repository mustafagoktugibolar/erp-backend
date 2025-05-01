// src/main/java/com/archproj/erp_backend/factories/ProductFactory.java
package com.archproj.erp_backend.factories;

import com.archproj.erp_backend.models.DigitalProduct;
import com.archproj.erp_backend.models.PhysicalProduct;
import com.archproj.erp_backend.models.Product;
import com.archproj.erp_backend.utils.ProductTypeEnum;

public class ProductFactory {
    public static Product createProduct(ProductTypeEnum type, String name, Double price) {
        return switch (type) {
            case PHYSICAL -> new PhysicalProduct(name, price);
            case DIGITAL  -> new DigitalProduct(name, price);
        };
    }
}
