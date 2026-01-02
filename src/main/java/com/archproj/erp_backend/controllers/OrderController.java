package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.services.OrderService;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.services.PaymentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;
    private final PaymentService paymentService;

    public OrderController(OrderService orderService, PaymentService paymentService) {
        this.orderService = orderService;
        this.paymentService = paymentService;
    }

    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderService.createOrder(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
    }

    @PostMapping("/{orderId}/items")
    public Order addItemToOrder(@PathVariable Long orderId, @RequestBody Long itemId) {
        return orderService.addItemToOrder(orderId, itemId);
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Long id, @RequestBody Order order) {
        return orderService.updateOrder(id, order);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public Order removeItemFromOrder(@PathVariable Long orderId, @PathVariable Long itemId) {
        return orderService.removeItemFromOrder(orderId, itemId);
    }

    @GetMapping("/{orderId}/items")
    public List<Long> getOrderItems(@PathVariable Long orderId) {
        return orderService.getOrderItems(orderId);
    }

    @PostMapping("/{orderId}/pay")
    public void payOrder(@PathVariable Long orderId, @RequestParam String method) {
        Order order = orderService.getOrderById(orderId);
        paymentService.pay(method, orderId, order.getTotalAmount());
    }

}
