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
        ProductEntity entity = productRepository.findById(id).orElse(null);
        return entity != null ? convertEntityToModel(entity) : null;
    }

    public Product createProduct(Product product) {
        ProductEntity entity = convertModelToEntity(product);
        ProductEntity savedEntity = productRepository.save(entity);
        return convertEntityToModel(savedEntity);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private Product convertEntityToModel(ProductEntity entity) {
        ProductTypeEnum type = ProductTypeEnum.valueOf(entity.getProductType());
        return ProductFactory.createProduct(type, entity.getName(), entity.getPrice());
    }

    private ProductEntity convertModelToEntity(Product model) {
        ProductEntity entity = new ProductEntity();
        entity.setName(model.getName());
        entity.setPrice(model.getPrice());
        entity.setProductType(model.getType().name());
        return entity;
    }
}
