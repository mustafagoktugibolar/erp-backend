package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;

public interface OrderObserver {
    void onOrderCreated(OrderEntity order);
    void onOrderCompleted(OrderEntity order);
}
