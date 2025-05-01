package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.ModuleTypes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModuleInfo {
    private long id;
    private String name;
    private String key;
    private String icon;
    private String route;
    private ModuleTypes type;
}
