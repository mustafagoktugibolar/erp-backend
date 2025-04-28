package com.archproj.erp_backend.config;

import com.archproj.erp_backend.entities.*;
import com.archproj.erp_backend.repositories.*;
import com.archproj.erp_backend.utils.CompanyTypeEnum;
import com.archproj.erp_backend.utils.CustomerTypeEnum;
import com.archproj.erp_backend.utils.OrderStatusEnum;
import com.archproj.erp_backend.utils.ProductTypeEnum;
import com.archproj.erp_backend.helper.LogHelper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Component
public class DummyDataLoader {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final Random random = new Random();

    public DummyDataLoader(
            CompanyRepository companyRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository
    ) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

     //@PostConstruct // Dummy data için açılır
    public void loadDummyData() {
        loadCompanies();
        loadCustomers();
        loadProducts();
        loadOrders();
    }

    private void loadCompanies() {
        List<CompanyEntity> companies = new ArrayList<>();
        IntStream.rangeClosed(1, 50).forEach(i -> {
            CompanyEntity company = new CompanyEntity();
            company.setName(generateCompanyName(i));
            company.setEmail(generateEmail(i));
            company.setType(randomEnumValue(CompanyTypeEnum.class));
            companies.add(company);
        });
        companyRepository.saveAll(companies);
        LogHelper.info(companies.size() + " companies created.");
    }

    private void loadCustomers() {
        List<CustomerEntity> customers = new ArrayList<>();
        IntStream.rangeClosed(1, 50).forEach(i -> {
            CustomerEntity customer = new CustomerEntity();
            customer.setName("Customer " + i);
            customer.setEmail("customer" + i + "@mail.com");
            customer.setCustomerType(randomEnumValue(CustomerTypeEnum.class));
            customers.add(customer);
        });
        customerRepository.saveAll(customers);
        LogHelper.info(customers.size() + " customers created.");
    }

    private void loadProducts() {
        List<ProductEntity> products = new ArrayList<>();
        IntStream.rangeClosed(1, 50).forEach(i -> {
            ProductEntity product = new ProductEntity();
            product.setName("Product " + i);
            product.setPrice(Math.round((50 + random.nextDouble() * 950) * 100.0) / 100.0); // 50 - 1000 arası fiyat
            product.setProductType(randomEnumValue(ProductTypeEnum.class));
            products.add(product);
        });
        productRepository.saveAll(products);
        LogHelper.info(products.size() + " products created.");
    }

    private void loadOrders() {
        List<CustomerEntity> customers = customerRepository.findAll();

        List<OrderEntity> orders = new ArrayList<>();

        IntStream.rangeClosed(1, 100).forEach(i -> {
            OrderEntity order = new OrderEntity();
            order.setCustomerId(customers.get(random.nextInt(customers.size())).getId());
            order.setOrderDate(LocalDateTime.now().minusDays(random.nextInt(30)));
            order.setStatus(OrderStatusEnum.CREATED);
            order.setTotalAmount(0.0);

            List<Long> itemIds = new ArrayList<>();

            int numberOfItems = 1 + random.nextInt(5);

            for (int j = 0; j < numberOfItems; j++) {
                long randomItemId = 1 + random.nextInt(50); // Example: random ID
                itemIds.add(randomItemId);
            }

            order.setItemIds(itemIds);

            orders.add(order);
        });

        orderRepository.saveAll(orders);

        LogHelper.info(orders.size() + " orders created with item IDs.");
    }



    private String generateCompanyName(int index) {
        String[] prefixes = {"Tech", "Global", "Solutions", "Dynamics", "Systems", "Innovations", "Industries", "Holdings"};
        String[] suffixes = {"Group", "Corp", "Inc", "LLC", "Ltd", "Networks", "Enterprises", "Technologies"};
        return prefixes[random.nextInt(prefixes.length)] + " " + suffixes[random.nextInt(suffixes.length)] + " " + index;
    }

    private String generateEmail(int index) {
        String[] domains = {"example.com", "corp.com", "business.net", "startup.io", "company.org"};
        return "contact" + index + "@" + domains[random.nextInt(domains.length)];
    }

    private <T extends Enum<?>> String randomEnumValue(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        return enumConstants[random.nextInt(enumConstants.length)].name();
    }
}
