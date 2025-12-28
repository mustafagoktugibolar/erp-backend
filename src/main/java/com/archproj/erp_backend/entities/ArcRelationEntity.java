package com.archproj.erp_backend.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "arc_relations")
public class ArcRelationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "source_type", nullable = false)
    private String sourceType;

    @Column(name = "source_id", nullable = false)
    private Long sourceId;

    @Column(name = "target_type", nullable = false)
    private String targetType;

    @Column(name = "target_id", nullable = false)
    private Long targetId;

    @Column(name = "relation_type")
    private String relationType; // e.g., "BELONGS_TO", "HAS_MANY"

    @Column(name = "settings", length = 4000)
    private String settings; // JSON rules

    public ArcRelationEntity(String sourceType, Long sourceId, String targetType, Long targetId, String relationType,
            String settings) {
        this.sourceType = sourceType;
        this.sourceId = sourceId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.relationType = relationType;
        this.settings = settings;
    }

    public ArcRelationEntity(String sourceType, Long sourceId, String targetType, Long targetId, String relationType) {
        this(sourceType, sourceId, targetType, targetId, relationType, null);
    }
}
