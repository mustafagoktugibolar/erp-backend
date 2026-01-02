package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.services.ArcRelationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/relations")
public class ArcRelationController {

    private final ArcRelationService arcRelationService;

    public ArcRelationController(ArcRelationService arcRelationService) {
        this.arcRelationService = arcRelationService;
    }

    public record CreateRelationRequest(String sourceType, Long sourceId, String targetType, Long targetId,
            String relationType, String settings) {
    }

    @PostMapping
    public ArcRelationEntity createRelation(@RequestBody CreateRelationRequest request) {
        return arcRelationService.createRelation(
                request.sourceType(),
                request.sourceId(),
                request.targetType(),
                request.targetId(),
                request.relationType(),
                request.settings());
    }

    @GetMapping
    public List<ArcRelationEntity> getRelations(
            @RequestParam(required = false) String sourceType,
            @RequestParam(required = false) Long sourceId,
            @RequestParam(required = false) String targetType,
            @RequestParam(required = false) Long targetId) {

        if (sourceType != null && sourceId != null) {
            return arcRelationService.getRelationsBySource(sourceType, sourceId);
        } else if (targetType != null && targetId != null) {
            return arcRelationService.getRelationsByTarget(targetType, targetId);
        } else {
            return List.of(); // Prevent dumping all relations without filter
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRelation(@PathVariable Long id) {
        arcRelationService.deleteRelation(id);
        return ResponseEntity.noContent().build();
    }
}
