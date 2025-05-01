package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.helper.LogHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationObserver implements OrderObserver {

    @Override
    public void onOrderCreated(OrderEntity order) {
        // TEMP
        LogHelper.info("Email sent to customer for order ID: " + order.getId());
    }

    @Override
    public void onOrderCompleted(OrderEntity order) {
        LogHelper.info("Email sent to customer for order ID: " + order.getId());
    }
}
