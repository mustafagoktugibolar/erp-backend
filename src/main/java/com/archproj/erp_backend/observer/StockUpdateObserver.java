package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.events.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StockUpdateObserver {

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        OrderEntity order = event.getOrder();
        log.info("Stock updated for order ID: " + order.getId());
    }
}
