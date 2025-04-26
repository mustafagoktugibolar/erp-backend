package com.archproj.erp_backend.factories;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.entities.OrderItemEntity;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.models.OrderItem;
import com.archproj.erp_backend.utils.OrderStatusEnum;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class OrderFactory {

    public static OrderEntity createOrder(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setCustomerId(order.getCustomerId());
        orderEntity.setOrderDate(LocalDateTime.now()); // Sipariş tarihi şimdi
        orderEntity.setStatus(OrderStatusEnum.CREATED); // İlk hali CREATED

        List<OrderItemEntity> itemEntities = order.getItems().stream()
                .map(item -> {
                    OrderItemEntity itemEntity = new OrderItemEntity();
                    itemEntity.setProductId(item.getProductId());
                    itemEntity.setProductName(item.getProductName());
                    itemEntity.setQuantity(item.getQuantity());
                    itemEntity.setUnitPrice(item.getUnitPrice());
                    itemEntity.setTotalPrice(item.getQuantity() * item.getUnitPrice());
                    itemEntity.setOrder(orderEntity); // Parent order bağlanıyor
                    return itemEntity;
                })
                .collect(Collectors.toList());

        orderEntity.setItems(itemEntities);

        // Toplam hesapla
        double totalAmount = itemEntities.stream()
                .mapToDouble(OrderItemEntity::getTotalPrice)
                .sum();
        orderEntity.setTotalAmount(totalAmount);

        return orderEntity;
    }
}
