// ArcObjectRepository.java
package com.archproj.erp_backend.repositories;

import com.archproj.erp_backend.entities.ArcObjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArcObjectRepository extends JpaRepository<ArcObjectEntity, Long> {
    List<ArcObjectEntity> findByModuleId(Long moduleId);
}
