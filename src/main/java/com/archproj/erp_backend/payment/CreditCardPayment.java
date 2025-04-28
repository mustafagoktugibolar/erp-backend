package com.archproj.erp_backend.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("creditCardPayment")
public class CreditCardPayment implements PaymentStrategy {
    @Override
    public void pay(Long orderId, Double amount) {
        log.info("Paid {}₺ with Credit Card for Order ID: {}", amount, orderId);
    }
}
