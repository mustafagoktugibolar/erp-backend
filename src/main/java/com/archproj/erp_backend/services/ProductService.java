// src/main/java/com/archproj/erp_backend/services/ProductService.java
package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ProductEntity;
import com.archproj.erp_backend.factories.ProductFactory;
import com.archproj.erp_backend.models.Product;
import com.archproj.erp_backend.repositories.ProductRepository;
import com.archproj.erp_backend.utils.ProductTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .map(this::convertEntityToModel)
                .orElse(null);
    }

    public Product createProduct(Product product) {
        ProductEntity entity = convertModelToEntity(product);
        ProductEntity saved = productRepository.save(entity);
        return convertEntityToModel(saved);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product convertEntityToModel(ProductEntity entity) {
        ProductTypeEnum type = ProductTypeEnum.valueOf(entity.getProductType());
        Product model = ProductFactory.createProduct(type, entity.getName(), entity.getPrice());
        model.setId(entity.getId());
        return model;
    }

    private ProductEntity convertModelToEntity(Product model) {
        ProductEntity entity = new ProductEntity();
        if (model.getId() != null) {
            entity.setId(model.getId());
        }
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setProductType(model.getType().name());
        return entity;
    }
}
