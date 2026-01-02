package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.events.OrderCompletedEvent;
import com.archproj.erp_backend.events.OrderCreatedEvent;
import com.archproj.erp_backend.repositories.ArcRelationRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.events.ArcObjectUpdatedEvent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class ArcRelationListener {

    private static final Logger log = LoggerFactory.getLogger(ArcRelationListener.class);

    private final ArcRelationRepository arcRelationRepository;
    private final ArcObjectService arcObjectService;
    private final com.archproj.erp_backend.repositories.ModuleRepository moduleRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ArcRelationListener(ArcRelationRepository arcRelationRepository, ArcObjectService arcObjectService,
            com.archproj.erp_backend.repositories.ModuleRepository moduleRepository) {
        this.arcRelationRepository = arcRelationRepository;
        this.arcObjectService = arcObjectService;
        this.moduleRepository = moduleRepository;
    }

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        Long orderId = event.getOrder().getId();
        log.info("ArcRelationListener: Order Created " + orderId);

        updateRelatedObjects("ORDER", orderId);
    }

    @EventListener
    public void onOrderCompleted(OrderCompletedEvent event) {
        Long orderId = event.getOrder().getId();
        log.info("ArcRelationListener: Order Completed " + orderId);

        updateRelatedObjects("ORDER", orderId);
    }

    @EventListener
    public void onArcObjectUpdated(ArcObjectUpdatedEvent event) {
        Long objectId = event.getArcObject().getArc_object_id();
        log.info("ArcRelationListener: ArcObject Updated " + objectId);

        processSmartTriggers(event.getArcObject());
    }

    private void processSmartTriggers(ArcObject sourceObject) {
        String sourceType = sourceObject.getObjectType(); // Dynamic type
        if (sourceType == null || "ARC_OBJECT".equals(sourceType)) {
            // Try to resolve generic "ARC_OBJECT" to specific Module Name (e.g. "deneme11")
            if (sourceObject.getModuleId() != null) {
                sourceType = moduleRepository.findById(sourceObject.getModuleId())
                        .map(com.archproj.erp_backend.entities.ModuleEntity::getName)
                        .orElse("ARC_OBJECT");
            } else {
                sourceType = "ARC_OBJECT"; // Fallback
            }
        }
        Long sourceId = sourceObject.getArc_object_id();

        log.info("Smart Trigger: Processing for SourceType: " + sourceType + ", SourceID: " + sourceId);

        List<ArcRelationEntity> relations = arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId);

        for (ArcRelationEntity relation : relations) {
            if (relation.getSettings() != null && !relation.getSettings().isEmpty()) {
                applyRule(sourceObject, relation);
            }
        }
    }

    private void applyRule(ArcObject sourceObject, ArcRelationEntity relation) {
        try {
            JsonNode settings = objectMapper.readTree(relation.getSettings());

            String triggerField = settings.path("triggerField").asText(null);
            String targetField = settings.path("targetField").asText(null);

            if (triggerField == null || targetField == null)
                return;

            // Check if source object has the trigger field
            Map<String, Object> sourceData = sourceObject.getData();
            if (!sourceData.containsKey(triggerField))
                return;

            Object val = sourceData.get(triggerField);
            String currentValue = val != null ? val.toString() : null;

            // valueMapping: { "ACTIVE": "VIP", "INACTIVE": "STANDARD" }
            JsonNode valueMapping = settings.path("valueMapping");

            if (valueMapping.has(currentValue)) {
                String targetValue = valueMapping.get(currentValue).asText();
                log.info("Smart Trigger: Rule Matched! Source Value: " + currentValue + " => Target Value: "
                        + targetValue);
                updateTargetObject(relation.getTargetType(), relation.getTargetId(), targetField, targetValue);
            } else {
                log.info("Smart Trigger: No rule match for value '" + currentValue + "'. Known mappings: "
                        + valueMapping.toString());
            }

        } catch (Exception e) {
            log.error("Error applying smart trigger for relation " + relation.getId(), e);
        }
    }

    private void updateTargetObject(String targetType, Long targetId, String field, String value) {
        log.info("Attempting to update target object. Type: " + targetType + ", ID: " + targetId + ", Field: " + field
                + ", New Value: " + value);

        // List of known static types that are NOT ArcObjects
        // (You can expand this list as needed based on your entity model)
        List<String> staticTypes = List.of("COMPANY", "ORDER", "INVOICE", "CUSTOMER", "PRODUCT");

        if (!staticTypes.contains(targetType)) {
            // Assume it's a Dynamic Module (ArcObject) if it's not a static type
            try {
                ArcObject target = arcObjectService.getById(targetId);
                if (target != null) {
                    Object existingValue = target.get(field);
                    String existingString = existingValue != null ? existingValue.toString() : null;

                    if (value.equals(existingString)) {
                        log.info("Smart Trigger: Value unchanged for " + targetType + " " + targetId + " field " + field
                                + ". Skipping update to prevent loop.");
                        return;
                    }

                    target.set(field, value);
                    arcObjectService.save(target);
                    log.info("Smart Trigger: Successfully updated " + targetType + " (ArcObject) " + targetId);
                } else {
                    log.warn("Smart Trigger: Target " + targetType + " with ID " + targetId + " not found.");
                }
            } catch (Exception e) {
                log.error("Smart Trigger: Failed to update target " + targetType + " " + targetId, e);
            }
        } else {
            log.warn("Smart Trigger: Update not yet implemented for static type: " + targetType);
        }
    }

    private void updateRelatedObjects(String sourceType, Long sourceId) {
        List<ArcRelationEntity> relations = arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId);
        for (ArcRelationEntity relation : relations) {
            log.info("Found relation to " + relation.getTargetType() + " ID: " + relation.getTargetId());
        }
    }
}
