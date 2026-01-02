package com.archproj.erp_backend.payment;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("creditCardPayment")
public class CreditCardPayment implements PaymentStrategy {
    private static final Logger log = LoggerFactory.getLogger(CreditCardPayment.class);

    @Override
    public void pay(Long orderId, Double amount) {
        log.info("Paid {}₺ with Credit Card for Order ID: {}", amount, orderId);
    }
}
