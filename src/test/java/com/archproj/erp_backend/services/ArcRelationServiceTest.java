package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.repositories.ArcRelationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArcRelationServiceTest {

    @Mock
    private ArcRelationRepository arcRelationRepository;

    @InjectMocks
    private ArcRelationService arcRelationService;

    @Test
    void createRelation_ShouldSaveAndReturnRelation() {
        // Arrange
        String sourceType = "COMPANY";
        Long sourceId = 1L;
        String targetType = "ARC_OBJECT";
        Long targetId = 2L;
        String relationType = "test-relation";
        String settings = "{}";

        ArcRelationEntity entity = new ArcRelationEntity(sourceType, sourceId, targetType, targetId, relationType,
                settings);
        when(arcRelationRepository.save(any(ArcRelationEntity.class))).thenReturn(entity);
        when(arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId))
                .thenReturn(Collections.emptyList());

        // Act
        ArcRelationEntity result = arcRelationService.createRelation(sourceType, sourceId, targetType, targetId,
                relationType, settings);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getSourceType()).isEqualTo(sourceType);
        verify(arcRelationRepository, times(1)).save(any(ArcRelationEntity.class));
    }

    @Test
    void createRelation_ShouldThrowIfDuplicate() {
        // Arrange
        String sourceType = "COMPANY";
        Long sourceId = 1L;
        String targetType = "ARC_OBJECT";
        Long targetId = 2L;
        String relationType = "test-relation";

        ArcRelationEntity existing = new ArcRelationEntity(sourceType, sourceId, targetType, targetId, relationType,
                "{}");
        when(arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId)).thenReturn(List.of(existing));

        // Act & Assert
        try {
            arcRelationService.createRelation(sourceType, sourceId, targetType, targetId, relationType, "{}");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage()).isEqualTo("Relation already exists");
        }
    }
}
