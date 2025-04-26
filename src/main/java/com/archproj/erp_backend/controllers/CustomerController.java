package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.factories.CustomerFactory;
import com.archproj.erp_backend.models.Customer;
import com.archproj.erp_backend.services.CustomerService;
import com.archproj.erp_backend.utils.CustomerTypeEnum;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Customer createCustomer(@RequestParam CustomerTypeEnum type,
                                   @RequestParam String name,
                                   @RequestParam String email) {
        Customer customer = CustomerFactory.createCustomer(type, name, email);
        return customerService.createCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
