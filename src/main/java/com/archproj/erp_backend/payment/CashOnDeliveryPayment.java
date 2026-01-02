package com.archproj.erp_backend.payment;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("cashOnDeliveryPayment")
public class CashOnDeliveryPayment implements PaymentStrategy {
    private static final Logger log = LoggerFactory.getLogger(CashOnDeliveryPayment.class);

    @Override
    public void pay(Long orderId, Double amount) {
        log.info("Paid {}₺ with Cash on Delivery for Order ID: {}", amount, orderId);
    }
}
