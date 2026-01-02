package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.events.OrderCreatedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class StockUpdateObserver {
    private static final Logger log = LoggerFactory.getLogger(StockUpdateObserver.class);

    @EventListener
    public void onOrderCreated(OrderCreatedEvent event) {
        OrderEntity order = event.getOrder();
        log.info("Stock updated for order ID: " + order.getId());
    }
}
