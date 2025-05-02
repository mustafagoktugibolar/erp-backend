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

    @ElementCollection
    @CollectionTable(name = "arc_object_data", joinColumns = @JoinColumn(name = "id"))
    @MapKeyColumn(name = "data_key")
    @Column(name = "data_value")
    private Map<String, String> data = new HashMap<>();


}
