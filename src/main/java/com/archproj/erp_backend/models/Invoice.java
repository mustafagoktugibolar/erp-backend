package com.archproj.erp_backend.models;

import com.archproj.erp_backend.utils.InvoiceStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private Long id;
    private Long orderId;
    private LocalDateTime issueDate;
    private Double amount;
    private String invoiceNumber;
    private InvoiceStatusEnum status;
}
