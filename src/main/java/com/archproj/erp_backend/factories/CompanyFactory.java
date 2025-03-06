package com.archproj.erp_backend.factories;

import com.archproj.erp_backend.models.Company;
import com.archproj.erp_backend.utils.CompanyTypeEnum;

public class CompanyFactory {
    public static Company createCompany(String name, String email, CompanyTypeEnum type) {
        return new Company(name, email, type);
    }
}
