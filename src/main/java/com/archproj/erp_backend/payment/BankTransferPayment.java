package com.archproj.erp_backend.payment;

import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component("bankTransferPayment")
public class BankTransferPayment implements PaymentStrategy {
    private static final Logger log = LoggerFactory.getLogger(BankTransferPayment.class);

    @Override
    public void pay(Long orderId, Double amount) {
        log.info("Paid {}₺ via Bank Transfer for Order ID: {}", amount, orderId);
    }
}
