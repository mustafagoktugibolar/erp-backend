package com.archproj.erp_backend.controllers;

import com.archproj.erp_backend.factories.CustomerFactory;
import com.archproj.erp_backend.models.Customer;
import com.archproj.erp_backend.services.CustomerService;
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
    public Customer createCustomer(@RequestBody Customer payload) {
        // use factory to build our domain object
        Customer customer = CustomerFactory.createCustomer(
                payload.getType(),
                payload.getName(),
                payload.getEmail());
        return customerService.createCustomer(customer);
    }

    @PutMapping("/{id}")
    public Customer editCustomer(
            @PathVariable Long id,
            @RequestBody Customer payload) {
        // factory produces the correct subclass/validation
        Customer customer = CustomerFactory.createCustomer(
                payload.getType(),
                payload.getName(),
                payload.getEmail());
        // now set the existing PK so service.save will update
        customer.setId(id);
        return customerService.updateCustomer(customer);
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
