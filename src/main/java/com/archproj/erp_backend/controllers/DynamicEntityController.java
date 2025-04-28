package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.services.DynamicEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/dynamic")
@RequiredArgsConstructor
public class DynamicEntityController {

    private final DynamicEntityService dynamicEntityService;

    @PostMapping("/upload")
    public String uploadExcel(@RequestParam("file") MultipartFile file,
                              @RequestParam("entityName") String entityName) {
        dynamicEntityService.processExcel(file, entityName);
        return "Excel processed and entity created successfully.";
    }
}
