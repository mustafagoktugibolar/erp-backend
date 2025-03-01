package com.archproj.erp_backend.repositories;

import com.archproj.erp_backend.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
