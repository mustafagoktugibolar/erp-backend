package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ArcRelationEntity;
import com.archproj.erp_backend.events.ArcObjectUpdatedEvent;
import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.repositories.ArcRelationRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ArcRelationListenerTest {

    @Mock
    private ArcRelationRepository arcRelationRepository;

    @Mock
    private ArcObjectService arcObjectService;

    @InjectMocks
    private ArcRelationListener arcRelationListener;

    @Test
    void onArcObjectUpdated_ShouldTriggerUpdate_WhenRuleMatches() {
        // Arrange
        Long sourceId = 1L;
        Long targetId = 2L;

        // Source Object (Trigger)
        ArcObject sourceObject = new ArcObject();
        sourceObject.setArc_object_id(sourceId);
        sourceObject.setObjectType("COMPANY");
        Map<String, Object> sourceData = new HashMap<>();
        sourceData.put("status", "ACTIVE");
        sourceObject.setData(sourceData);

        // Relation with Rule
        String settings = "{\"triggerField\":\"status\", \"targetField\":\"clientStatus\", \"valueMapping\":{\"ACTIVE\":\"VIP\"}}";
        ArcRelationEntity relation = new ArcRelationEntity("COMPANY", sourceId, "ARC_OBJECT", targetId, "SYNC",
                settings);

        when(arcRelationRepository.findBySourceTypeAndSourceId("COMPANY", sourceId))
                .thenReturn(List.of(relation));

        // Target Object (To be updated)
        ArcObject targetObject = new ArcObject();
        targetObject.setArc_object_id(targetId);
        targetObject.setData(new HashMap<>()); // Empty data initially

        when(arcObjectService.getById(targetId)).thenReturn(targetObject);

        // Act
        arcRelationListener.onArcObjectUpdated(new ArcObjectUpdatedEvent(this, sourceObject));

        // Assert
        // Verify target object was saved
        verify(arcObjectService).save(targetObject);
        // Verify value was updated
        assert (targetObject.getData().get("clientStatus").equals("VIP"));
    }

    @Test
    void onArcObjectUpdated_ShouldNotTrigger_WhenFieldDoesNotMatch() {
        // Arrange
        Long sourceId = 1L;
        ArcObject sourceObject = new ArcObject();
        sourceObject.setArc_object_id(sourceId);
        sourceObject.setObjectType("COMPANY");
        Map<String, Object> sourceData = new HashMap<>();
        sourceData.put("status", "INACTIVE"); // Does not match mapping
        sourceObject.setData(sourceData);

        String settings = "{\"triggerField\":\"status\", \"targetField\":\"clientStatus\", \"valueMapping\":{\"ACTIVE\":\"VIP\"}}";
        ArcRelationEntity relation = new ArcRelationEntity("COMPANY", sourceId, "ARC_OBJECT", 2L, "SYNC", settings);

        when(arcRelationRepository.findBySourceTypeAndSourceId("COMPANY", sourceId))
                .thenReturn(List.of(relation));

        // Act
        arcRelationListener.onArcObjectUpdated(new ArcObjectUpdatedEvent(this, sourceObject));

        // Assert
        verify(arcObjectService, never()).save(any());
    }
}
