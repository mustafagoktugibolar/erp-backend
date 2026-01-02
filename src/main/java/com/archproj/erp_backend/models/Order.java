package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long customerId;
    private LocalDateTime orderDate;
    private OrderStatusEnum status;
    private Double totalAmount;
    private List<Long> itemIds;

    public Order() {
    }

    public Order(Long id, Long customerId, LocalDateTime orderDate, OrderStatusEnum status, Double totalAmount,
            List<Long> itemIds) {
        this.id = id;
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.status = status;
        this.totalAmount = totalAmount;
        this.itemIds = itemIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<Long> getItemIds() {
        return itemIds;
    }

    public void setItemIds(List<Long> itemIds) {
        this.itemIds = itemIds;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", itemIds=" + itemIds +
                '}';
    }
}
