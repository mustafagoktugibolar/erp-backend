package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.events.OrderCompletedEvent;
import com.archproj.erp_backend.events.OrderCreatedEvent;
import com.archproj.erp_backend.repositories.ArcRelationRepository;
import com.archproj.erp_backend.services.ArcObjectService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ArcRelationListener {

    private final ArcRelationRepository arcRelationRepository;
    private final ArcObjectService arcObjectService;

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        Long orderId = event.getOrder().getId();
        log.info("ArcRelationListener: Order Created " + orderId);

        // Example: Find related dynamic objects and update them
        updateRelatedObjects("ORDER", orderId);
    }

    @EventListener
    public void onOrderCompleted(OrderCompletedEvent event) {
        Long orderId = event.getOrder().getId();
        log.info("ArcRelationListener: Order Completed " + orderId);

        // Example trigger
        updateRelatedObjects("ORDER", orderId);
    }

    private void updateRelatedObjects(String sourceType, Long sourceId) {
        List<ArcRelationEntity> relations = arcRelationRepository.findBySourceTypeAndSourceId(sourceType, sourceId);
        for (ArcRelationEntity relation : relations) {
            log.info("Found relation to " + relation.getTargetType() + " ID: " + relation.getTargetId());
            // Here we would call arcObjectService to update the target object
            // For now, just logging as a placeholder for the logic
        }
    }
}
