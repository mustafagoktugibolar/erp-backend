package com.archproj.erp_backend.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private String status;
    private Double totalAmount;
    private List<Long> itemIds;
}
