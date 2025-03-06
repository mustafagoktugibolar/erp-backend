package com.archproj.erp_backend.factories;

import com.archproj.erp_backend.models.Customer;
import com.archproj.erp_backend.models.Order;

public class OrderFactory {
    public static Order createOrder(String orderId, String status, Customer customer) {
        return new Order(null, orderId, status, customer);
    }
}
