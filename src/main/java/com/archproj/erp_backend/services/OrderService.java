package com.archproj.erp_backend.services;

import com.archproj.erp_backend.dtos.OrderDTO;
import com.archproj.erp_backend.factories.OrderFactory;
import com.archproj.erp_backend.mappers.DTOMapper;
import com.archproj.erp_backend.models.Customer;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.observer.OrderObserver;
import com.archproj.erp_backend.repositories.CustomerRepository;
import com.archproj.erp_backend.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private final List<OrderObserver> observers = new ArrayList<>();

    public void addObserver(OrderObserver observer) {
        observers.add(observer);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(DTOMapper::toOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        return DTOMapper.toOrderDTO(order);
    }

    public OrderDTO createOrder(String orderId, String status, Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        Order order = OrderFactory.createOrder(orderId, status, customer);
        order = orderRepository.save(order);
        notifyObservers("Order " + orderId + " has been placed.");

        return DTOMapper.toOrderDTO(order);
    }

    private void notifyObservers(String message) {
        for (OrderObserver observer : observers) {
            observer.update(message);
        }
    }
}
