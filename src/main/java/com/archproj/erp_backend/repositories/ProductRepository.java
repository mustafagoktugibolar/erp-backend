package com.archproj.erp_backend.repositories;

import com.archproj.erp_backend.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
