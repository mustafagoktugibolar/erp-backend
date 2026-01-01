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
    private final org.springframework.context.ApplicationEventPublisher eventPublisher;

    public CompanyService(CompanyRepository companyRepository,
            org.springframework.context.ApplicationEventPublisher eventPublisher) {
        this.companyRepository = companyRepository;
        this.eventPublisher = eventPublisher;
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
        Company result = convertEntityToModel(savedEntity);
        publishUpdateEvent(result);
        return result;
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
        if (payload.getData() != null) {
            entity.setData(payload.getData());
        }

        // 3) save & convert back to model
        CompanyEntity saved = companyRepository.save(entity);
        Company result = convertEntityToModel(saved);
        publishUpdateEvent(result);
        return result;
    }

    // In CompanyService
    private Company convertEntityToModel(CompanyEntity entity) {
        Company company = new Company(entity.getName(), entity.getEmail(), CompanyTypeEnum.valueOf(entity.getType()));
        company.setId(entity.getId()); // <- Add this line!
        company.setData(new java.util.HashMap<>(entity.getData()));
        return company;
    }

    private CompanyEntity convertModelToEntity(Company model) {
        CompanyEntity entity = new CompanyEntity();
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        entity.setType(model.getType().name());
        entity.setData(model.getData());
        return entity;
    }

    private void publishUpdateEvent(Company company) {
        // Map Company to ArcObject Structure for the generic listener
        com.archproj.erp_backend.models.ArcObject arcObj = new com.archproj.erp_backend.models.ArcObject();
        arcObj.setArc_object_id(company.getId());
        // We need a Module ID for Company? Or we treat Company as a "Static Module"
        // source?
        // Let's assume Module ID -1 or check how listener distinguishes.
        // The Listener uses sourceType="ARC_OBJECT" by default. We should probably tell
        // it "sourceType=COMPANY".
        // But the current implementation of listener assumes ARC_OBJECT.
        // Let's stick with ArcObject wrapper.
        // We can put all Company fields into 'data' map of ArcObject so rules can
        // access 'name', 'type', etc.
        arcObj.setModuleId(-1L); // Special ID for Core Entities?
        arcObj.setObjectType("COMPANY");

        java.util.Map<String, Object> data = new java.util.HashMap<>(company.getData());
        data.put("name", company.getName());
        data.put("email", company.getEmail());
        data.put("type", company.getType().name());
        arcObj.setData(data);

        eventPublisher.publishEvent(new com.archproj.erp_backend.events.ArcObjectUpdatedEvent(this, arcObj));
    }
}
