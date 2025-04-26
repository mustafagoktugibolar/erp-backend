package com.archproj.erp_backend.factories;

import com.archproj.erp_backend.models.DigitalProduct;
import com.archproj.erp_backend.models.PhysicalProduct;
import com.archproj.erp_backend.models.Product;
import com.archproj.erp_backend.utils.ProductTypeEnum;

public class ProductFactory {

    public static Product createProduct(ProductTypeEnum type, String name, Double price) {
        if (type == ProductTypeEnum.PHYSICAL) {
            return new PhysicalProduct(name, price);
        } else if (type == ProductTypeEnum.DIGITAL) {
            return new DigitalProduct(name, price);
        }
        throw new IllegalArgumentException("Invalid product type");
    }
}
