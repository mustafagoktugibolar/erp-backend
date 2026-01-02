package com.archproj.erp_backend.models;

public class ColumnInfo {
    private String name;
    private String type;
    private boolean isEditable;

    public ColumnInfo() {
    }

    public ColumnInfo(String name, String type, boolean isEditable) {
        this.name = name;
        this.type = type;
        this.isEditable = isEditable;
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

    @Override
    public String toString() {
        return "ColumnInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isEditable=" + isEditable +
                '}';
    }
}
