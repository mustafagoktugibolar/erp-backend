package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.CustomerEntity;
import com.archproj.erp_backend.factories.CustomerFactory;
import com.archproj.erp_backend.models.Customer;
import com.archproj.erp_backend.repositories.CustomerRepository;
import com.archproj.erp_backend.utils.CustomerTypeEnum;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    public Customer getCustomerById(Long id) {
        CustomerEntity entity = customerRepository.findById(id).orElse(null);
        return entity != null ? convertEntityToModel(entity) : null;
    }

    public Customer createCustomer(Customer customer) {
        CustomerEntity entity = convertModelToEntity(customer);
        CustomerEntity savedEntity = customerRepository.save(entity);
        return convertEntityToModel(savedEntity);
    }

    public Customer updateCustomer(Customer customer) {
        // load existing, copy fields, save
        CustomerEntity e = customerRepository.findById(customer.getId())
                .orElseThrow(() -> new RuntimeException("Customer not found: " + customer.getId()));

        e.setName(customer.getName());
        e.setEmail(customer.getEmail());
        e.setCustomerType(customer.getType().name());

        CustomerEntity saved = customerRepository.save(e);
        return convertEntityToModel(saved);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    private Customer convertEntityToModel(CustomerEntity entity) {
        CustomerTypeEnum type = CustomerTypeEnum.valueOf(entity.getCustomerType());
        return CustomerFactory.createCustomer(entity.getId(), type, entity.getName(), entity.getEmail());
    }

    private CustomerEntity convertModelToEntity(Customer model) {
        CustomerEntity entity = new CustomerEntity();
        entity.setName(model.getName());
        entity.setEmail(model.getEmail());
        entity.setCustomerType(model.getType().name());
        return entity;
    }
}
