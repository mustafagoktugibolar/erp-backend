package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.entities.OrderItemEntity;
import com.archproj.erp_backend.factories.OrderFactory;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.models.OrderItem;
import com.archproj.erp_backend.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public OrderEntity createOrder(Order order) {
        OrderEntity orderEntity = OrderFactory.createOrder(order);
        return orderRepository.save(orderEntity);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderEntity addItemToOrder(Long orderId, OrderItem orderItem) {
        OrderEntity order = getOrderById(orderId);

        OrderItemEntity itemEntity = new OrderItemEntity();
        itemEntity.setProductId(orderItem.getProductId());
        itemEntity.setProductName(orderItem.getProductName());
        itemEntity.setQuantity(orderItem.getQuantity());
        itemEntity.setUnitPrice(orderItem.getUnitPrice());
        itemEntity.setTotalPrice(orderItem.getQuantity() * orderItem.getUnitPrice());
        itemEntity.setOrder(order);

        order.getItems().add(itemEntity);

        // totalAmount güncelle
        double newTotal = order.getItems().stream()
                .mapToDouble(OrderItemEntity::getTotalPrice)
                .sum();
        order.setTotalAmount(newTotal);

        return orderRepository.save(order);
    }

    public OrderEntity updateItemInOrder(Long orderId, Long itemId, OrderItem orderItem) {
        OrderEntity order = getOrderById(orderId);

        OrderItemEntity itemEntity = order.getItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("OrderItem not found with id: " + itemId));

        itemEntity.setProductId(orderItem.getProductId());
        itemEntity.setProductName(orderItem.getProductName());
        itemEntity.setQuantity(orderItem.getQuantity());
        itemEntity.setUnitPrice(orderItem.getUnitPrice());
        itemEntity.setTotalPrice(orderItem.getQuantity() * orderItem.getUnitPrice());

        // totalAmount güncelle
        double newTotal = order.getItems().stream()
                .mapToDouble(OrderItemEntity::getTotalPrice)
                .sum();
        order.setTotalAmount(newTotal);

        return orderRepository.save(order);
    }

    public OrderEntity removeItemFromOrder(Long orderId, Long itemId) {
        OrderEntity order = getOrderById(orderId);

        boolean removed = order.getItems().removeIf(item -> item.getId().equals(itemId));
        if (!removed) {
            throw new RuntimeException("OrderItem not found with id: " + itemId);
        }

        // totalAmount güncelle
        double newTotal = order.getItems().stream()
                .mapToDouble(OrderItemEntity::getTotalPrice)
                .sum();
        order.setTotalAmount(newTotal);

        return orderRepository.save(order);
    }

    public List<OrderItemEntity> getOrderItems(Long orderId) {
        OrderEntity order = getOrderById(orderId);
        return order.getItems();
    }

}
