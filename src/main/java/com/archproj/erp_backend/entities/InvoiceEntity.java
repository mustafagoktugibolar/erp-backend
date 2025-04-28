package com.archproj.erp_backend.entities;

import com.archproj.erp_backend.utils.InvoiceStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "invoices")
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    private LocalDateTime issueDate;

    private Double amount;

    private String invoiceNumber;

    private String status;

}
