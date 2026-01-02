package com.archproj.erp_backend.entities;

import jakarta.persistence.*;

@Entity
public class ColumnEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long columnId;

    private String name;
    private String type;
    private boolean isEditable;

    // back‐ref to ModuleEntity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", nullable = false)
    private ModuleEntity module;

    public Long getColumnId() {
        return columnId;
    }

    public void setColumnId(Long columnId) {
        this.columnId = columnId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEditable() {
        return isEditable;
    }

    public void setEditable(boolean isEditable) {
        this.isEditable = isEditable;
    }

    public ModuleEntity getModule() {
        return module;
    }

    public void setModule(ModuleEntity module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "ColumnEntity{" +
                "columnId=" + columnId +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isEditable=" + isEditable +
                '}';
    }
}
