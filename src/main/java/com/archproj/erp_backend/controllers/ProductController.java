package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.dtos.ProductDTO;
import com.archproj.erp_backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductDTO getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PostMapping
    public ProductDTO createProduct(@RequestParam String name,
                                    @RequestParam double price) {
        return productService.createProduct(name, price);
    }

    @PutMapping("/{id}/discount")
    public double applyDiscount(@PathVariable Long id, @RequestParam double percentage) {
        return productService.applyDiscount(id, percentage);
    }

    @PutMapping("/{id}/premium")
    public double applyPremium(@PathVariable Long id, @RequestParam double percentage) {
        return productService.applyPremium(id, percentage);
    }
}
