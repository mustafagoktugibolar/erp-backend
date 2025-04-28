package com.archproj.erp_backend.services;

import com.archproj.erp_backend.payment.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final Map<String, PaymentStrategy> paymentStrategies;

    public void pay(String method, Long orderId, Double amount) {
        PaymentStrategy strategy = paymentStrategies.get(method);
        if (strategy == null) {
            throw new IllegalArgumentException("Unsupported payment method: " + method);
        }
        strategy.pay(orderId, amount);
    }
}
