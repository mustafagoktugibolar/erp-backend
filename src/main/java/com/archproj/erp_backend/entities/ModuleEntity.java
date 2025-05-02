package com.archproj.erp_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "modules")
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;     // Display name of the module
    private String key;      // Unique identifier used in frontend routing
    private String route;    // Route path (e.g., "/products")
    private String icon;     // Icon name (e.g., "inventory_2")\
    private String type;

    @OneToMany(
            mappedBy = "module",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ColumnEntity> columns = new ArrayList<>();
}
