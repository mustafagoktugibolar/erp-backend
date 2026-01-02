package com.archproj.erp_backend.entities;

import jakarta.persistence.*;

@Entity
public class ArcObjectEntity extends BaseArcEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ArcObjectEntity{" +
                "id=" + id +
                '}';
    }
}
