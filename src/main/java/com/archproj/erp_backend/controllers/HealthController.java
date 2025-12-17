package com.archproj.erp_backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/probe")
    public Map<String, String> probe() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "UP");
        status.put("message", "ERP Backend is running");
        return status;
    }
}
