// ArcObjectEntity.java
package com.archproj.erp_backend.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity
@Table(name = "arc_objects")
public class ArcObjectEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "module_id", nullable = false)
    private Long moduleId;

    @Column(name = "data", columnDefinition = "TEXT")
    @Convert(converter = com.archproj.erp_backend.utils.JpaJsonConverter.class)
    private Map<String, Object> data = new HashMap<>();

}
