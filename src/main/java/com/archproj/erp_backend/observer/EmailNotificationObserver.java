package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.events.OrderCompletedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class EmailNotificationObserver {
    private static final Logger log = LoggerFactory.getLogger(EmailNotificationObserver.class);

    @EventListener
    public void onOrderCompleted(OrderCompletedEvent event) {
        OrderEntity order = event.getOrder();
        log.info("Email sent to customer for order ID: " + order.getId());
    }
}
