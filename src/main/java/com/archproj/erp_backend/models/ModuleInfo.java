package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.ModuleTypes;

import java.util.List;

public class ModuleInfo {
    private long id;
    private String name;
    private String key;
    private String icon;
    private String route;
    private ModuleTypes type;
    private List<ColumnInfo> columns;

    public ModuleInfo() {
    }

    public ModuleInfo(long id, String name, String key, String icon, String route, ModuleTypes type,
            List<ColumnInfo> columns) {
        this.id = id;
        this.name = name;
        this.key = key;
        this.icon = icon;
        this.route = route;
        this.type = type;
        this.columns = columns;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public ModuleTypes getType() {
        return type;
    }

    public void setType(ModuleTypes type) {
        this.type = type;
    }

    public List<ColumnInfo> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnInfo> columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return "ModuleInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", icon='" + icon + '\'' +
                ", route='" + route + '\'' +
                ", type=" + type +
                ", columns=" + columns +
                '}';
    }
}
