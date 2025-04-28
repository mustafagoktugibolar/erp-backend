package com.archproj.erp_backend.factories;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.utils.OrderStatusEnum;

import java.time.LocalDateTime;

public class OrderFactory {

    public static OrderEntity createOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setCustomerId(order.getCustomerId());
        orderEntity.setOrderDate(LocalDateTime.now());
        orderEntity.setStatus(OrderStatusEnum.CREATED);
        orderEntity.setItemIds(order.getItemIds());
        orderEntity.setTotalAmount(order.getTotalAmount());

        return orderEntity;
    }
}
