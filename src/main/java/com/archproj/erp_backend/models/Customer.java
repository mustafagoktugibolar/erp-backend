package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.CustomerTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Customer {
    private String name;
    private String email;
    public abstract CustomerTypeEnum getType();
}
