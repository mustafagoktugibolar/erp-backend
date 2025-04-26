package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.entities.OrderItemEntity;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.models.OrderItem;
import com.archproj.erp_backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public List<OrderEntity> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderEntity getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderEntity createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }
    @PostMapping("/{orderId}/items")
    public OrderEntity addItemToOrder(@PathVariable Long orderId, @RequestBody OrderItem orderItem) {
        return orderService.addItemToOrder(orderId, orderItem);
    }

    @PutMapping("/{orderId}/items/{itemId}")
    public OrderEntity updateItemInOrder(@PathVariable Long orderId, @PathVariable Long itemId, @RequestBody OrderItem orderItem) {
        return orderService.updateItemInOrder(orderId, itemId, orderItem);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public OrderEntity removeItemFromOrder(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.removeItemFromOrder(orderId, itemId);
    }

    @GetMapping("/{orderId}/items")
    public List<OrderItemEntity> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }
}
