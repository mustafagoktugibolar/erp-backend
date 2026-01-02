package com.archproj.erp_backend.observer;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.services.InvoiceService;
import org.springframework.stereotype.Component;

@Component
public class InvoiceObserver implements OrderObserver {

    private final InvoiceService invoiceService;

    public InvoiceObserver(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    public void onOrderCreated(OrderEntity order) {
    }

    @Override
    public void onOrderCompleted(OrderEntity order) {
        invoiceService.createInvoice(order.getId(), order.getTotalAmount());
    }
}
