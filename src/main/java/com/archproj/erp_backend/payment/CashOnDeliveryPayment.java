package com.archproj.erp_backend.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("cashOnDeliveryPayment")
public class CashOnDeliveryPayment implements PaymentStrategy {
    @Override
    public void pay(Long orderId, Double amount) {
        log.info("Paid {}₺ with Cash on Delivery for Order ID: {}", amount, orderId);
    }
}
