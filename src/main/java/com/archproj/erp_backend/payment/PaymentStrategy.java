package com.archproj.erp_backend.payment;

public interface PaymentStrategy {
    void pay(Long orderId, Double amount);
}
