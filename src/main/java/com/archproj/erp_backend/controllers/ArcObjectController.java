package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.models.ArcObject;
import com.archproj.erp_backend.services.ArcObjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules/{moduleId}/objects")
@CrossOrigin(
        origins       = "http://localhost:3000",
        methods       = {
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
        // service.save will INSERT because payload.getId() is null
        return arcObjectService.save(payload);
    }

    @PutMapping("/{id}")
    public ArcObject edit(
            @PathVariable Long moduleId,
            @PathVariable Long id,
            @RequestBody ArcObject payload
    ) {
        payload.setModuleId(moduleId);
        payload.setId(id);
        return arcObjectService.save(payload);
    }

    @DeleteMapping("/{id}")
    public void delete(
            @PathVariable Long moduleId,
            @PathVariable Long id
    ) {
        arcObjectService.deleteByModuleIdAndId(moduleId, id);
    }
}
