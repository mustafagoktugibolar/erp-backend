package com.archproj.erp_backend.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // Display name of the module
    private String key; // Unique identifier used in frontend routing
    private String route; // Route path (e.g., "/products")
    private String icon; // Icon name (e.g., "inventory_2")\
    private String type;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ColumnEntity> columns = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "ModuleEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", route='" + route + '\'' +
                ", icon='" + icon + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
}
