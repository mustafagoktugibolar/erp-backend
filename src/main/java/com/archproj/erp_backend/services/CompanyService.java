package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.CompanyEntity;
import com.archproj.erp_backend.models.Company;
import com.archproj.erp_backend.repositories.CompanyRepository;
import com.archproj.erp_backend.utils.CompanyTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    public Company getCompanyById(Long id) {
        CompanyEntity entity = companyRepository.findById(id).orElse(null);
        return entity != null ? convertEntityToModel(entity) : null;
    }

    public Company createCompany(Company company) {
        CompanyEntity entity = convertModelToEntity(company);
        CompanyEntity savedEntity = companyRepository.save(entity);
        return convertEntityToModel(savedEntity);
    }

    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    public Company updateCompany(Long id, Company payload) {
        // 1) fetch existing entity (or throw if not found)
        CompanyEntity entity = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found: " + id));

        // 2) copy over the updatable fields
        entity.setName(payload.getName());
        entity.setEmail(payload.getEmail());
        entity.setType(payload.getType().name());

        // 3) save & convert back to model
        CompanyEntity saved = companyRepository.save(entity);
        return convertEntityToModel(saved);
    }

    // In CompanyService
    private Company convertEntityToModel(CompanyEntity entity) {
        Company company = new Company(entity.getName(), entity.getEmail(), CompanyTypeEnum.valueOf(entity.getType()));
        company.setId(entity.getId()); // <- Add this line!
        return company;
    }

    private CompanyEntity convertModelToEntity(Company model) {
        CompanyEntity entity = new CompanyEntity();
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        entity.setType(model.getType().name());
        return entity;
    }
}
