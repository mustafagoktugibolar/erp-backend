package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ArcObjectEntity;
import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.repositories.ArcObjectRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArcObjectService {

    private final ArcObjectRepository arcObjectRepository;
    private final org.springframework.context.ApplicationEventPublisher eventPublisher;

    public ArcObjectService(ArcObjectRepository arcObjectRepository,
            org.springframework.context.ApplicationEventPublisher eventPublisher) {
        this.arcObjectRepository = arcObjectRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<ArcObject> getAllByModuleId(Long moduleId) {
        return arcObjectRepository.findByModuleId(moduleId)
                .stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    @Transactional
    public ArcObject save(ArcObject model) {
        ArcObjectEntity entity;

        // Check if we're editing an existing one
        if (model.getArc_object_id() != null) {
            entity = arcObjectRepository.findById(model.getArc_object_id()).orElse(new ArcObjectEntity());
        } else {
            entity = new ArcObjectEntity();
        }

        // Update fields
        entity.setId(model.getArc_object_id()); // may be null for insert
        entity.setModuleId(model.getModuleId());

        // Convert data (ensure String values)
        entity.setData(model.getData());

        // Save and return result
        ArcObjectEntity savedEntity = arcObjectRepository.save(entity);
        ArcObject result = convertEntityToModel(savedEntity);

        eventPublisher.publishEvent(new com.archproj.erp_backend.events.ArcObjectUpdatedEvent(this, result));

        return result;
    }

    public ArcObject getByModuleIdAndId(Long moduleId, Long id) {
        return arcObjectRepository.findById(id)
                .filter(obj -> obj.getModuleId().equals(moduleId))
                .map(this::convertEntityToModel)
                .orElse(null);
    }

    public ArcObject getById(Long id) {
        return arcObjectRepository.findById(id)
                .map(this::convertEntityToModel)
                .orElse(null);
    }

    public void deleteByModuleIdAndId(Long moduleId, Long id) {
        arcObjectRepository.findById(id).ifPresent(entity -> {
            if (entity.getModuleId().equals(moduleId)) {
                arcObjectRepository.deleteById(id);
            }
        });
    }

    private ArcObject convertEntityToModel(ArcObjectEntity entity) {
        ArcObject model = new ArcObject();
        model.setArc_object_id(entity.getId());
        model.setModuleId(entity.getModuleId());
        model.setData(new HashMap<>(entity.getData())); // Currently Map<String, String>
        return model;
    }

}
