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
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ArcRelationListener(ArcRelationRepository arcRelationRepository, ArcObjectService arcObjectService) {
        this.arcRelationRepository = arcRelationRepository;
        this.arcObjectService = arcObjectService;
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
        if (sourceType == null)
            sourceType = "ARC_OBJECT"; // Fallback
        Long sourceId = sourceObject.getArc_object_id();

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
                updateTargetObject(relation.getTargetType(), relation.getTargetId(), targetField, targetValue);
            }

        } catch (Exception e) {
            log.error("Error applying smart trigger for relation " + relation.getId(), e);
        }
    }

    private void updateTargetObject(String targetType, Long targetId, String field, String value) {

        if ("ARC_OBJECT".equals(targetType)) {
            ArcObject target = arcObjectService.getById(targetId);
            if (target != null) {
                target.set(field, value);
                arcObjectService.save(target);
                log.info("Smart Trigger: Updated ArcObject " + targetId + " field " + field + " to " + value);
            } else {
                log.warn("Smart Trigger: Target ArcObject " + targetId + " not found.");
            }
        }
    }

    private void updateRelatedObjects(String sourceType, Long sourceId) {
        List<ArcRelationEntity> relations = arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId);
        for (ArcRelationEntity relation : relations) {
            log.info("Found relation to " + relation.getTargetType() + " ID: " + relation.getTargetId());
        }
    }
}
