package com.archproj.erp_backend.repositories;

import com.archproj.erp_backend.models.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
}
