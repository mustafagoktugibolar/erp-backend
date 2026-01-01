package com.archproj.erp_backend.config;

import com.archproj.erp_backend.entities.*;
import com.archproj.erp_backend.repositories.*;
import com.archproj.erp_backend.utils.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
public class DummyDataLoader implements CommandLineRunner {

    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final ModuleRepository moduleRepository;
    private final Random random = new Random();

    @Override
    public void run(String... args) throws Exception {
        if (companyRepository.count() == 0) {
            loadDummyData();
        }
    }

    // @PostConstruct // Dummy data için açılır
    public void loadDummyData() {
        loadCompanies();
        loadCustomers();
        loadProducts();
        loadOrders();
        loadModules();
    }

    private void loadModules() {
        List<ModuleEntity> modules = List.of(
                createModule("Companies", "companies", "/companies", "business", ModuleTypes.CREATION.name()),
                createModule("Customers", "customers", "/customers", "people", ModuleTypes.CREATION.name()),
                createModule("Products", "products", "/products", "inventory_2", ModuleTypes.CREATION.name()),
                createModule("Orders", "orders", "/orders", "receipt_long", ModuleTypes.CREATION.name()),
                createModule("Invoices", "invoices", "/invoices", "receipt", ModuleTypes.CREATION.name()));

        moduleRepository.saveAll(modules);
        log.info(modules.size() + " default modules created.");
    }

    private ModuleEntity createModule(String name, String key, String route, String icon, String type) {
        ModuleEntity module = new ModuleEntity();
        module.setName(name);
        module.setKey(key);
        module.setRoute(route);
        module.setIcon(icon);
        module.setType(type);
        return module;
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
        log.info(companies.size() + " companies created.");
    }

    private void loadCustomers() {
        List<CustomerEntity> customers = new ArrayList<>();
        IntStream.rangeClosed(1, 50).forEach(i -> {
            CustomerEntity customer = new CustomerEntity();
            customer.setName("Customer " + i);
            customer.setEmail("customer" + i + "@mail.com");
            customer.setType(randomEnumValue(CustomerTypeEnum.class));
            customers.add(customer);
        });
        customerRepository.saveAll(customers);
        log.info(customers.size() + " customers created.");
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
        log.info(products.size() + " products created.");
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

        log.info(orders.size() + " orders created with item IDs.");
    }

    private String generateCompanyName(int index) {
        String[] prefixes = { "Tech", "Global", "Solutions", "Dynamics", "Systems", "Innovations", "Industries",
                "Holdings" };
        String[] suffixes = { "Group", "Corp", "Inc", "LLC", "Ltd", "Networks", "Enterprises", "Technologies" };
        return prefixes[random.nextInt(prefixes.length)] + " " + suffixes[random.nextInt(suffixes.length)] + " "
                + index;
    }

    private String generateEmail(int index) {
        String[] domains = { "example.com", "corp.com", "business.net", "startup.io", "company.org" };
        return "contact" + index + "@" + domains[random.nextInt(domains.length)];
    }

    private <T extends Enum<?>> String randomEnumValue(Class<T> enumClass) {
        T[] enumConstants = enumClass.getEnumConstants();
        return enumConstants[random.nextInt(enumConstants.length)].name();
    }
}
