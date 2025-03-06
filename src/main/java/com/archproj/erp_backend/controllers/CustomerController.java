package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.dtos.CustomerDTO;
import com.archproj.erp_backend.services.CustomerService;
import com.archproj.erp_backend.utils.CustomerTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestParam String type,
                                      @RequestParam String name,
                                      @RequestParam String email) {
        CustomerTypeEnum customerType = CustomerTypeEnum.valueOf(type.toUpperCase());
        return customerService.createCustomer(customerType, name, email);
    }
}