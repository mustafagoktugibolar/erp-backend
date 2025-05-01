package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ModuleEntity;
import com.archproj.erp_backend.models.ModuleInfo;
import com.archproj.erp_backend.repositories.ModuleRepository;
import com.archproj.erp_backend.utils.ModuleTypes;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public List<ModuleInfo> getAllModules() {
        return moduleRepository.findAll().stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    public ModuleInfo getModuleById(Long id) {
        ModuleEntity entity = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));
        return convertEntityToModel(entity);
    }

    public ModuleInfo createModule(ModuleInfo module) {
        ModuleEntity savedEntity = moduleRepository.save(convertModelToEntity(module));
        return convertEntityToModel(savedEntity);
    }

    public ModuleInfo updateModule(Long id, ModuleInfo module) {
        ModuleEntity existing = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));

        existing.setName(module.getName());
        existing.setKey(module.getKey());
        existing.setRoute(module.getRoute());
        existing.setIcon(module.getIcon());
        existing.setType(module.getType().name());

        ModuleEntity updated = moduleRepository.save(existing);
        return convertEntityToModel(updated);
    }

    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

    private ModuleInfo convertEntityToModel(ModuleEntity entity) {
        ModuleInfo model = new ModuleInfo();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setKey(entity.getKey());
        model.setRoute(entity.getRoute());
        model.setIcon(entity.getIcon());
        model.setType(Enum.valueOf(ModuleTypes.class, entity.getType()));
        return model;
    }

    private ModuleEntity convertModelToEntity(ModuleInfo model) {
        ModuleEntity entity = new ModuleEntity();
        entity.setId(model.getId());
        entity.setName(model.getName());
        entity.setKey(model.getKey());
        entity.setRoute(model.getRoute());
        entity.setIcon(model.getIcon());
        entity.setType(model.getType().name());
        return entity;
    }
}
