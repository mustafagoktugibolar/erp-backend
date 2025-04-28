package com.archproj.erp_backend.payment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component("bankTransferPayment")
public class BankTransferPayment implements PaymentStrategy {
    @Override
    public void pay(Long orderId, Double amount) {
        log.info("Paid {}₺ via Bank Transfer for Order ID: {}", amount, orderId);
    }
}
