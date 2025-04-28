package com.archproj.erp_backend.repositories;

import com.archproj.erp_backend.entities.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long> {
}
