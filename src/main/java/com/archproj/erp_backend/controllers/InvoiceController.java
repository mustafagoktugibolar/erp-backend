package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.entities.InvoiceEntity;
import com.archproj.erp_backend.models.Invoice;
import com.archproj.erp_backend.services.InvoiceService;
import com.archproj.erp_backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final OrderService orderService;

    @GetMapping
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable Long id) {
        return invoiceService.getInvoiceById(id);
    }

    @PostMapping("/{orderId}/generate")
    public InvoiceEntity generateInvoice(@PathVariable Long orderId) {
        Double amount = orderService.getOrderById(orderId).getTotalAmount();
        return invoiceService.createInvoice(orderId, amount);
    }

    @DeleteMapping("/{id}")
    public void deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
    }
}
