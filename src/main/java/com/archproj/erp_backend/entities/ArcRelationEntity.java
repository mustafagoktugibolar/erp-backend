package com.archproj.erp_backend.entities;

import jakarta.persistence.*;

@Entity
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

    public ArcRelationEntity() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public Long getSourceId() {
        return sourceId;
    }

    public void setSourceId(Long sourceId) {
        this.sourceId = sourceId;
    }

    public String getTargetType() {
        return targetType;
    }

    public void setTargetType(String targetType) {
        this.targetType = targetType;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public String getRelationType() {
        return relationType;
    }

    public void setRelationType(String relationType) {
        this.relationType = relationType;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    @Override
    public String toString() {
        return "ArcRelationEntity{" +
                "id=" + id +
                ", sourceType='" + sourceType + '\'' +
                ", sourceId=" + sourceId +
                ", targetType='" + targetType + '\'' +
                ", targetId=" + targetId +
                ", relationType='" + relationType + '\'' +
                ", settings='" + settings + '\'' +
                '}';
    }
}
