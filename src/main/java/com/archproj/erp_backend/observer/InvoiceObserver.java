package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.services.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InvoiceObserver implements OrderObserver {

    private final InvoiceService invoiceService;

    @Override
    public void onOrderCreated(OrderEntity order) {
    }

    @Override
    public void onOrderCompleted(OrderEntity order) {
        invoiceService.createInvoice(order.getId(), order.getTotalAmount());
    }
}
