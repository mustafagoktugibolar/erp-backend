package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.events.OrderCompletedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailNotificationObserver {

    @EventListener
    public void onOrderCompleted(OrderCompletedEvent event) {
        OrderEntity order = event.getOrder();
        log.info("Email sent to customer for order ID: " + order.getId());
    }
}
