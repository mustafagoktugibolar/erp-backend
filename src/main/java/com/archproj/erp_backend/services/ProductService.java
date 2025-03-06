package com.archproj.erp_backend.services;

import com.archproj.erp_backend.dtos.ProductDTO;
import com.archproj.erp_backend.models.Product;
import com.archproj.erp_backend.repositories.ProductRepository;
import com.archproj.erp_backend.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StandardPricing standardPricing;

    @Autowired
    private DiscountPricing discountPricing;

    @Autowired
    private PremiumPricing premiumPricing;

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(p -> new ProductDTO(p.getId(), p.getName(), p.getPrice()))
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }

    public ProductDTO createProduct(String name, double basePrice) {
        double finalPrice = standardPricing.getPrice(basePrice);
        Product product = new Product(name, finalPrice);
        product = productRepository.save(product);
        return new ProductDTO(product.getId(), product.getName(), product.getPrice());
    }

    public double applyDiscount(Long productId, double discountPercentage) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double newPrice = discountPricing.applyDiscount(product.getPrice(), discountPercentage);
        product.setPrice(newPrice);
        productRepository.save(product);

        return newPrice;
    }

    public double applyPremium(Long productId, double premiumPercentage) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        double newPrice = premiumPricing.applyPremium(product.getPrice(), premiumPercentage);
        product.setPrice(newPrice);
        productRepository.save(product);

        return newPrice;
    }
}
