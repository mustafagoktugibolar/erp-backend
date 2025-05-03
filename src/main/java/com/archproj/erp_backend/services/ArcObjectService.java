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

    public ArcObjectService(ArcObjectRepository arcObjectRepository) {
        this.arcObjectRepository = arcObjectRepository;
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
        Map<String, String> convertedMap = model.getData().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> String.valueOf(e.getValue())
                ));
        entity.setData(convertedMap);

        // Save and return result
        ArcObjectEntity savedEntity = arcObjectRepository.save(entity);
        return convertEntityToModel(savedEntity);
    }



    public ArcObject getByModuleIdAndId(Long moduleId, Long id) {
        return arcObjectRepository.findById(id)
                .filter(obj -> obj.getModuleId().equals(moduleId))
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

    private ArcObjectEntity convertModelToEntity(ArcObject model) {
        ArcObjectEntity entity = new ArcObjectEntity();
        entity.setId(model.getArc_object_id());
        entity.setModuleId(model.getModuleId());

        Map<String, String> convertedMap = model.getData().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> String.valueOf(e.getValue())
                ));

        entity.setData(convertedMap);
        return entity;
    }


}
