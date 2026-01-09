package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.InvoiceEntity;
import com.archproj.erp_backend.models.Invoice;
import com.archproj.erp_backend.repositories.InvoiceRepository;
import com.archproj.erp_backend.utils.InvoiceStatusEnum;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;

    public InvoiceService(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(this::convertEntityToModel)
                .orElse(null);
    }

    public InvoiceEntity createInvoice(Long orderId, Double amount) {
        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setOrderId(orderId);
        invoice.setAmount(amount);
        invoice.setIssueDate(LocalDateTime.now());
        invoice.setStatus("CREATED");

        return invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }

    private Invoice convertEntityToModel(InvoiceEntity entity) {
        return new Invoice(
                entity.getId(),
                entity.getOrderId(),
                entity.getIssueDate(),
                entity.getAmount(),
                entity.getInvoiceNumber(),
                InvoiceStatusEnum.valueOf(entity.getStatus()) // Enum -> String
        );
    }
}
