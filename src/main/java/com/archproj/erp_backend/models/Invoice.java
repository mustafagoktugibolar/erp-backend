package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.InvoiceStatusEnum;

import java.time.LocalDateTime;

public class Invoice {
    private Long id;
    private Long orderId;
    private LocalDateTime issueDate;
    private Double amount;
    private String invoiceNumber;
    private InvoiceStatusEnum status;

    public Invoice() {
    }

    public Invoice(Long id, Long orderId, LocalDateTime issueDate, Double amount, String invoiceNumber,
            InvoiceStatusEnum status) {
        this.id = id;
        this.orderId = orderId;
        this.issueDate = issueDate;
        this.amount = amount;
        this.invoiceNumber = invoiceNumber;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDateTime issueDate) {
        this.issueDate = issueDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public InvoiceStatusEnum getStatus() {
        return status;
    }

    public void setStatus(InvoiceStatusEnum status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", issueDate=" + issueDate +
                ", amount=" + amount +
                ", invoiceNumber='" + invoiceNumber + '\'' +
                ", status=" + status +
                '}';
    }
}
