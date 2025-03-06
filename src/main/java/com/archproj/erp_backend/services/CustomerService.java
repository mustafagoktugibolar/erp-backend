package com.archproj.erp_backend.services;

import com.archproj.erp_backend.dtos.CustomerDTO;
import com.archproj.erp_backend.factories.CustomerFactory;
import com.archproj.erp_backend.mappers.DTOMapper;
import com.archproj.erp_backend.models.Customer;
import com.archproj.erp_backend.repositories.CustomerRepository;
import com.archproj.erp_backend.utils.CustomerTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(DTOMapper::toCustomerDTO)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return DTOMapper.toCustomerDTO(customer);
    }

    public CustomerDTO createCustomer(CustomerTypeEnum type, String name, String email) {
        Customer customer = CustomerFactory.createCustomer(type, name, email);
        customer = customerRepository.save(customer);
        return DTOMapper.toCustomerDTO(customer);
    }
}
