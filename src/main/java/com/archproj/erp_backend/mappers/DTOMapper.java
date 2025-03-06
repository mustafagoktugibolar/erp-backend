package com.archproj.erp_backend.mappers;

import com.archproj.erp_backend.dtos.*;
import com.archproj.erp_backend.models.*;
import com.archproj.erp_backend.utils.CustomerTypeEnum;

public class DTOMapper {

    public static CustomerDTO toCustomerDTO(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                (customer instanceof com.archproj.erp_backend.models.CorporateCustomer)
                        ? CustomerTypeEnum.CORPORATE
                        : CustomerTypeEnum.INDIVIDUAL
        );
    }

    public static CompanyDTO toCompanyDTO(Company company) {
        return new CompanyDTO(
                company.getId(),
                company.getName(),
                company.getEmail(),
                company.getCompanyType()
        );
    }

    public static ProductDTO toProductDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getPrice()
        );
    }

    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getOrderId(),
                order.getStatus()
        );
    }
}
