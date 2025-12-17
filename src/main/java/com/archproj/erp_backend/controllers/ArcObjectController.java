package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.services.ArcObjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules/{moduleId}/objects")

public class ArcObjectController {

    private final ArcObjectService arcObjectService;
    private final com.archproj.erp_backend.services.ExcelService excelService;

    public ArcObjectController(ArcObjectService arcObjectService,
            com.archproj.erp_backend.services.ExcelService excelService) {
        this.arcObjectService = arcObjectService;
        this.excelService = excelService;
    }

    @GetMapping
    public List<ArcObject> getAll(@PathVariable Long moduleId) {
        return arcObjectService.getAllByModuleId(moduleId);
    }

    @GetMapping("/{id}")
    public ArcObject getOne(@PathVariable Long moduleId, @PathVariable Long id) {
        return arcObjectService.getByModuleIdAndId(moduleId, id);
    }

    @PostMapping
    public ArcObject create(@RequestBody ArcObject payload) {
        return arcObjectService.save(payload);
    }

    @PostMapping("/upload")
    public List<ArcObject> uploadExcel(
            @PathVariable Long moduleId,
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file) throws java.io.IOException {

        List<java.util.Map<String, Object>> rows = excelService.parseExcelFile(file);
        List<ArcObject> savedObjects = new java.util.ArrayList<>();

        for (java.util.Map<String, Object> rowData : rows) {
            ArcObject obj = new ArcObject();
            obj.setModuleId(moduleId);
            obj.setData(rowData);
            savedObjects.add(arcObjectService.save(obj));
        }

        return savedObjects;
    }

    @PutMapping("/{id}")
    public ArcObject edit(
            @PathVariable Long moduleId,
            @PathVariable Long id,
            @RequestBody ArcObject payload) {
        payload.setModuleId(moduleId);
        payload.setArc_object_id(id);
        return arcObjectService.save(payload); // Update because ID is set
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long moduleId,
            @PathVariable Long id) {
        arcObjectService.deleteByModuleIdAndId(moduleId, id);
    }
}
