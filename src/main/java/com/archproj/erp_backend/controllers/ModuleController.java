package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.models.ModuleInfo;
import com.archproj.erp_backend.services.ModuleService;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/modules")
@CrossOrigin(origins = "http://localhost:3000")
public class ModuleController {
    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @GetMapping
    public List<ModuleInfo> getModules() {
        List<ModuleInfo> mi = new ArrayList<>();
        mi.addAll(moduleService.getAllModules());
        return moduleService.getAllModules();
    }
    @GetMapping("/{id}")
    public ModuleInfo getModuleById(@PathVariable Long id) {
        return moduleService.getModuleById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteModuleById(@PathVariable Long id) {
        moduleService.deleteModule(id);
    }
    @PutMapping("/{moduleId}")
    public ModuleInfo updateModule(@PathVariable Long moduleId, @RequestBody ModuleInfo moduleInfo) {
        return moduleService.updateModule(moduleId, moduleInfo);
    }

    @PostMapping
    public ModuleInfo addModule(@RequestBody ModuleInfo moduleInfo) {
        return moduleService.createModule(moduleInfo);
    }

}
