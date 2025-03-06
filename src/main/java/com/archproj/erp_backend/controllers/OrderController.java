package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.dtos.OrderDTO;
import com.archproj.erp_backend.observer.EmailNotificationService;
import com.archproj.erp_backend.observer.StockUpdateService;
import com.archproj.erp_backend.services.OrderService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailNotificationService emailNotificationService;

    @Autowired
    private StockUpdateService stockUpdateService;

    // ✅ This runs AFTER dependencies are injected
    @PostConstruct
    public void registerObservers() {
        orderService.addObserver(emailNotificationService);
        orderService.addObserver(stockUpdateService);
    }

    @GetMapping
    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public OrderDTO getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @PostMapping
    public OrderDTO createOrder(@RequestParam String orderId,
                                @RequestParam String status,
                                @RequestParam Long customerId) {
        return orderService.createOrder(orderId, status, customerId);
    }
}
