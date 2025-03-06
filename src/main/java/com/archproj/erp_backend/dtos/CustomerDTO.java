package com.archproj.erp_backend.dtos;

import com.archproj.erp_backend.utils.CustomerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    private Long id;
    private String name;
    private String email;
    private CustomerTypeEnum type;
}
