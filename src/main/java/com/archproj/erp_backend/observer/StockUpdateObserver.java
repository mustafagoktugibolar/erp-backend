package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.helper.LogHelper;
import org.springframework.stereotype.Service;

@Service
public class StockUpdateObserver implements OrderObserver {

    @Override
    public void onOrderCreated(OrderEntity order) {
        // TEMP
        LogHelper.info("Stock updated for order ID: " + order.getId());
    }
}
