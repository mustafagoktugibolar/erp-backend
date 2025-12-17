package com.archproj.erp_backend.repositories;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArcRelationRepository extends JpaRepository<ArcRelationEntity, Long> {
    List<ArcRelationEntity> findBySourceTypeAndSourceId(String sourceType, Long sourceId);

    List<ArcRelationEntity> findByTargetTypeAndTargetId(String targetType, Long targetId);

    void deleteBySourceTypeAndSourceId(String sourceType, Long sourceId);
}
