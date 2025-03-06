package com.archproj.erp_backend.services;

import com.archproj.erp_backend.dtos.CompanyDTO;
import com.archproj.erp_backend.factories.CompanyFactory;
import com.archproj.erp_backend.mappers.DTOMapper;
import com.archproj.erp_backend.models.Company;
import com.archproj.erp_backend.repositories.CompanyRepository;
import com.archproj.erp_backend.utils.CompanyTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public List<CompanyDTO> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(DTOMapper::toCompanyDTO)
                .collect(Collectors.toList());
    }

    public CompanyDTO getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));
        return DTOMapper.toCompanyDTO(company);
    }

    public CompanyDTO createCompany(String name, String email, CompanyTypeEnum type) {
        Company company = CompanyFactory.createCompany(name, email, type);
        company = companyRepository.save(company);
        return DTOMapper.toCompanyDTO(company);
    }
}
