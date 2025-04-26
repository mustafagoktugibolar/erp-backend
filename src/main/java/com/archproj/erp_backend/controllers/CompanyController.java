package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.dtos.CompanyDTO;
import com.archproj.erp_backend.services.CompanyService;
import com.archproj.erp_backend.utils.CompanyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping
    public List<CompanyDTO> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    public CompanyDTO getCompanyById(@PathVariable Long id) {
        return companyService.getCompanyById(id);
    }

    @PostMapping
    public CompanyDTO createCompany(@RequestParam String name,
                                    @RequestParam String email,
                                    @RequestParam CompanyTypeEnum type) {
        return companyService.createCompany(name, email, type);
    }
}
