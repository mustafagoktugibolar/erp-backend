package com.archproj.erp_backend.dtos;

import com.archproj.erp_backend.utils.CompanyTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDTO {
    private Long id;
    private String name;
    private String email;
    private CompanyTypeEnum type;
}
