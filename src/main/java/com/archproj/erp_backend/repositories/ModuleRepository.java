package com.archproj.erp_backend.repositories;

import com.archproj.erp_backend.entities.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
    java.util.Optional<ModuleEntity> findByName(String name);
}
