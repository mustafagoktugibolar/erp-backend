package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.OrderEntity;

import com.archproj.erp_backend.mappers.OrderMapper;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.repositories.OrderRepository;
import com.archproj.erp_backend.utils.OrderStatusEnum;
import com.archproj.erp_backend.exceptions.ResourceNotFoundException;

import jakarta.transaction.Transactional;
import com.archproj.erp_backend.events.OrderCompletedEvent;
import com.archproj.erp_backend.events.OrderCreatedEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper,
            ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.eventPublisher = eventPublisher;
    }

    /**
     * Fetch all orders and convert them to your model.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toModel)
                .collect(Collectors.toList());
    }

    public Order getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + id));
        return orderMapper.toModel(entity);
    }

    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found: " + id));

        OrderStatusEnum previousStatus = entity.getStatus();

        entity.setCustomerId(updatedOrder.getCustomerId());
        entity.setItemIds(updatedOrder.getItemIds());
        entity.setTotalAmount(updatedOrder.getTotalAmount());
        entity.setOrderDate(updatedOrder.getOrderDate());
        entity.setStatus(updatedOrder.getStatus());

        OrderEntity saved = orderRepository.save(entity);

        if (previousStatus != saved.getStatus() && "COMPLETED".equals(saved.getStatus().name())) {
            eventPublisher.publishEvent(new OrderCompletedEvent(this, saved));
        }

        return orderMapper.toModel(saved);
    }

    @Transactional
    public Order createOrder(Order model) {
        OrderEntity toSave = orderMapper.toEntity(model);
        OrderEntity saved = orderRepository.save(toSave);

        eventPublisher.publishEvent(new OrderCreatedEvent(this, saved));
        return orderMapper.toModel(saved);
    }

    public Order addItemToOrder(Long orderId, Long itemId) {
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        entity.getItemIds().add(itemId);
        OrderEntity saved = orderRepository.save(entity);

        return orderMapper.toModel(saved);
    }

    public Order removeItemFromOrder(Long orderId, Long itemId) {
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));

        boolean removed = entity.getItemIds().remove(itemId);
        if (!removed) {
            throw new ResourceNotFoundException("Item not found in order: " + itemId);
        }
        OrderEntity saved = orderRepository.save(entity);

        return orderMapper.toModel(saved);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }

    /**
     * Fetch just the raw list of item IDs.
     */
    public List<Long> getOrderItems(Long orderId) {
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id: " + orderId));
        return entity.getItemIds();
    }

    // ────────────────────────────────────────────────────────────────────────────
    // Converter methods
    // ────────────────────────────────────────────────────────────────────────────

}
