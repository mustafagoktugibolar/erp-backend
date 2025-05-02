package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.services.ArcObjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/modules/{moduleId}/objects")
@CrossOrigin(
        origins = "http://localhost:3000",
        methods = {
                RequestMethod.GET,
                RequestMethod.POST,
                RequestMethod.PUT,
                RequestMethod.DELETE,
                RequestMethod.OPTIONS
        },
        allowedHeaders = "*"
)
public class ArcObjectController {

    private final ArcObjectService arcObjectService;

    public ArcObjectController(ArcObjectService arcObjectService) {
        this.arcObjectService = arcObjectService;
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
    public ArcObject create(
            @PathVariable Long moduleId,
            @RequestBody ArcObject payload
    ) {
        payload.setModuleId(moduleId);
        return arcObjectService.save(payload); // Insert because ID is null
    }

    @PutMapping("/{id}")
    public ArcObject edit(
            @PathVariable Long moduleId,
            @PathVariable Long id,
            @RequestBody ArcObject payload
    ) {
        payload.setModuleId(moduleId);
        payload.setArc_object_id(id);
        return arcObjectService.save(payload); // Update because ID is set
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long moduleId,
            @PathVariable Long id
    ) {
        arcObjectService.deleteByModuleIdAndId(moduleId, id);
    }
}
