package com.archproj.erp_backend.factories;

import com.archproj.erp_backend.models.CorporateCustomer;
import com.archproj.erp_backend.models.Customer;
import com.archproj.erp_backend.models.IndividualCustomer;
import com.archproj.erp_backend.utils.CustomerTypeEnum;

public class CustomerFactory {
    public static Customer createCustomer(CustomerTypeEnum type, String name, String email) {
        if (type == CustomerTypeEnum.CORPORATE) {
            return new CorporateCustomer(name, email);
        } else if (type == CustomerTypeEnum.INDIVIDUAL) {
            return new IndividualCustomer(name, email);
        }
        throw new IllegalArgumentException("Invalid customer type");
    }
}
