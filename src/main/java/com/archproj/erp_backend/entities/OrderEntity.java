package com.archproj.erp_backend.entities;

import com.archproj.erp_backend.utils.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrderEntity extends BaseArcEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long customerId;

    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatusEnum status;

    private Double totalAmount;

    @ElementCollection
    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
    @Column(name = "item_id")
    private List<Long> itemIds;
}
