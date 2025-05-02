package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.ColumnEntity;
import com.archproj.erp_backend.entities.ModuleEntity;
import com.archproj.erp_backend.models.ColumnInfo;
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
        // 1) load existing module
        ModuleEntity existing = moduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Module not found with id: " + id));

        // 2) update basic fields
        existing.setName(module.getName());
        existing.setKey(module.getKey());
        existing.setRoute(module.getRoute());
        existing.setIcon(module.getIcon());
        existing.setType(module.getType().name());

        // 3) replace columns
        existing.getColumns().clear();
        if (module.getColumns() != null) {
            for (ColumnInfo ci : module.getColumns()) {
                ColumnEntity ce = new ColumnEntity();
                ce.setName(ci.getName());
                ce.setType(ci.getType());// or setEditable(...) depending on your Lombok setup
                ce.setModule(existing);
                existing.getColumns().add(ce);
            }
        }

        // 4) save everything in one go
        ModuleEntity updated = moduleRepository.save(existing);
        return convertEntityToModel(updated);
    }


    public void deleteModule(Long id) {
        moduleRepository.deleteById(id);
    }

    private ModuleInfo convertEntityToModel(ModuleEntity e) {
        ModuleInfo dto = new ModuleInfo();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setKey(e.getKey());
        dto.setRoute(e.getRoute());
        dto.setIcon(e.getIcon());
        dto.setType(Enum.valueOf(ModuleTypes.class, e.getType()));

        List<ColumnInfo> cols = e.getColumns().stream()
                .map(colEnt -> {
                    ColumnInfo ci = new ColumnInfo();
                    ci.setName(colEnt.getName());
                    ci.setType(colEnt.getType());
                    ci.setEditable(colEnt.isEditable());
                    return ci;
                })
                .collect(Collectors.toList());
        dto.setColumns(cols);

        return dto;
    }

    private ModuleEntity convertModelToEntity(ModuleInfo dto) {
        ModuleEntity e = new ModuleEntity();
        e.setName(dto.getName());
        e.setKey(dto.getKey());
        e.setRoute(dto.getRoute());
        e.setIcon(dto.getIcon());
        e.setType(dto.getType().name());

        // map incoming ColumnInfo into ColumnEntity
        if (dto.getColumns() != null) {
            List<ColumnEntity> cols = dto.getColumns().stream()
                    .map(ci -> {
                        ColumnEntity ent = new ColumnEntity();
                        ent.setName(ci.getName());
                        ent.setType(ci.getType());
                        ent.setEditable(ci.isEditable());
                        ent.setModule(e);
                        return ent;
                    })
                    .collect(Collectors.toList());
            e.getColumns().clear();
            e.getColumns().addAll(cols);
        }
        return e;
    }
}
