package com.archproj.erp_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "arc_objects")
public class ArcObjectEntity extends BaseArcEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id", nullable = false)
    private Long moduleId;

}
