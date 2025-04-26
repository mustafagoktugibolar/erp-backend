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
    private final OrderItemRepository orderItemRepository;
    private final Random random = new Random();

    public DummyDataLoader(
            CompanyRepository companyRepository,
            CustomerRepository customerRepository,
            ProductRepository productRepository,
            OrderRepository orderRepository,
            OrderItemRepository orderItemRepository
    ) {
        this.companyRepository = companyRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
    }

    //@PostConstruct
    public void loadDummyData() {
        loadCompanies();
        loadCustomers();
        loadProducts();
        loadOrdersAndOrderItems();
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
            product.setPrice(Math.round((50 + random.nextDouble() * 950) * 100.0) / 100.0); // 50 - 1000 range
            product.setProductType(randomEnumValue(ProductTypeEnum.class));
            products.add(product);
        });
        productRepository.saveAll(products);
        LogHelper.info(products.size() + " products created.");
    }

    private void loadOrdersAndOrderItems() {
        List<CustomerEntity> customers = customerRepository.findAll();
        List<ProductEntity> products = productRepository.findAll();

        List<OrderEntity> orders = new ArrayList<>();
        List<OrderItemEntity> orderItems = new ArrayList<>();

        IntStream.rangeClosed(1, 100).forEach(i -> {
            OrderEntity order = new OrderEntity();
            order.setCustomerId(customers.get(random.nextInt(customers.size())).getId());
            order.setOrderDate(LocalDateTime.now().minusDays(random.nextInt(30)));
            order.setStatus(OrderStatusEnum.CREATED);
            order.setTotalAmount(0.0);

            orders.add(order);
        });

        orderRepository.saveAll(orders);

        orders.forEach(order -> {
            int numberOfItems = 1 + random.nextInt(5);
            double totalAmount = 0.0;

            for (int j = 0; j < numberOfItems; j++) {
                ProductEntity product = products.get(random.nextInt(products.size()));
                int quantity = 1 + random.nextInt(10);
                double unitPrice = product.getPrice();
                double totalPrice = unitPrice * quantity;

                OrderItemEntity item = new OrderItemEntity();
                item.setOrder(order);
                item.setProductId(product.getId());
                item.setProductName(product.getName());
                item.setQuantity(quantity);
                item.setUnitPrice(unitPrice);
                item.setTotalPrice(Math.round(totalPrice * 100.0) / 100.0); // İki ondalık

                totalAmount += totalPrice;
                orderItems.add(item);
            }

            order.setTotalAmount(Math.round(totalAmount * 100.0) / 100.0);
        });

        orderItemRepository.saveAll(orderItems);

        LogHelper.info(orders.size() + " orders and " + orderItems.size() + " order items created.");
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
