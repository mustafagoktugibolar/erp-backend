package com.archproj.erp_backend.entities;

import com.archproj.erp_backend.utils.OrderStatusEnum;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
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
        return "OrderEntity{" +
                "id=" + id +
                ", customerId=" + customerId +
                ", orderDate=" + orderDate +
                ", status=" + status +
                ", totalAmount=" + totalAmount +
                ", itemIds=" + itemIds +
                '}';
    }
}
