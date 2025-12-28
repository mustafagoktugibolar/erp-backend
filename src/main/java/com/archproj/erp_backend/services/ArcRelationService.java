package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.repositories.ArcRelationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArcRelationService {

    private final ArcRelationRepository arcRelationRepository;

    @Transactional
    public ArcRelationEntity createRelation(String sourceType, Long sourceId, String targetType, Long targetId,
            String relationType, String settings) {
        // Prevent duplicate relations if needed, or allow multiples?
        // For now allowing multiples as per generic design, but typically unique
        // constraint might be better.
        // Let's check existence first to avoid simple duplicates.
        List<ArcRelationEntity> existing = arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId);
        boolean exists = existing.stream()
                .anyMatch(r -> r.getTargetType().equals(targetType) && r.getTargetId().equals(targetId)
                        && r.getRelationType().equals(relationType));

        if (exists) {
            throw new IllegalArgumentException("Relation already exists");
        }

        ArcRelationEntity relation = new ArcRelationEntity(sourceType, sourceId, targetType, targetId, relationType,
                settings);
        return arcRelationRepository.save(relation);
    }

    public List<ArcRelationEntity> getRelationsBySource(String sourceType, Long sourceId) {
        return arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId);
    }

    public List<ArcRelationEntity> getRelationsByTarget(String targetType, Long targetId) {
        return arcRelationRepository.findByTargetTypeAndTargetId(targetType, targetId);
    }

    @Transactional
    public void deleteRelation(Long id) {
        arcRelationRepository.deleteById(id);
    }
}
