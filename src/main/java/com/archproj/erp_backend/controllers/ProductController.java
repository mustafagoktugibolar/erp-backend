package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.factories.ProductFactory;
import com.archproj.erp_backend.models.Product;
import com.archproj.erp_backend.services.ProductService;
import com.archproj.erp_backend.utils.ProductTypeEnum;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public Product createProduct(@RequestParam ProductTypeEnum type,
                                 @RequestParam String name,
                                 @RequestParam Double price) {
        Product product = ProductFactory.createProduct(type, name, price);
        return productService.createProduct(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
