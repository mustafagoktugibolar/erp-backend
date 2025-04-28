package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.factories.OrderFactory;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.observer.OrderObserver;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import com.archproj.erp_backend.repositories.OrderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final List<OrderObserver> observers;

    public List<OrderEntity> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderEntity getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }

    public OrderEntity createOrder(Order order) {
        OrderEntity orderEntity = OrderFactory.createOrder(order);
        OrderEntity savedOrder = orderRepository.save(orderEntity);
        observers.forEach(observer -> observer.onOrderCreated(savedOrder)); // ⭐
        return savedOrder;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    public OrderEntity addItemToOrder(Long orderId, Long itemId) {
        OrderEntity order = getOrderById(orderId);

        order.getItemIds().add(itemId);
        return orderRepository.save(order);
    }

    public OrderEntity removeItemFromOrder(Long orderId, Long itemId) {
        OrderEntity order = getOrderById(orderId);

        boolean removed = order.getItemIds().remove(itemId);
        if (!removed) {
            throw new RuntimeException("Item not found in order: " + itemId);
        }
        return orderRepository.save(order);
    }

    public List<Long> getOrderItems(Long orderId) {
        OrderEntity order = getOrderById(orderId);
        return order.getItemIds();
    }
}
