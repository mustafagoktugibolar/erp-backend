package com.archproj.erp_backend.services;

import com.archproj.erp_backend.entities.OrderEntity;
import com.archproj.erp_backend.factories.OrderFactory;
import com.archproj.erp_backend.models.Order;
import com.archproj.erp_backend.observer.OrderObserver;
import com.archproj.erp_backend.repositories.OrderRepository;
import com.archproj.erp_backend.utils.OrderStatusEnum;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final List<OrderObserver> observers;

    /**
     * Fetch all orders and convert them to your model.
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertEntityToModel)
                .collect(Collectors.toList());
    }

    /**
     * Fetch a single order by ID and convert it to your model.
     */
    public Order getOrderById(Long id) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        return convertEntityToModel(entity);
    }

    /**
     * Update an existing order, notify observers, and return the updated model.
     */
    @Transactional
    public Order updateOrder(Long id, Order updatedOrder) {
        OrderEntity entity = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found: " + id));

        OrderStatusEnum previousStatus = entity.getStatus();

        // Alanları güncelle
        entity.setCustomerId(updatedOrder.getCustomerId());
        entity.setItemIds(updatedOrder.getItemIds());
        entity.setTotalAmount(updatedOrder.getTotalAmount());
        entity.setOrderDate(updatedOrder.getOrderDate());
        entity.setStatus(updatedOrder.getStatus());

        OrderEntity saved = orderRepository.save(entity);


        // 2) “COMPLETED” durumuna geçilmişse, invoice yaratmak için onOrderCompleted çağrısı
        if (previousStatus != saved.getStatus() && "COMPLETED".equals(saved.getStatus().name())){
            observers.forEach(o -> o.onOrderCompleted(saved));
        }

        return convertEntityToModel(saved);
    }

    /**
     * Create (or update) an order.  ID == null ⇒ INSERT; ID != null ⇒ UPDATE.
     */
    @Transactional
    public Order createOrder(Order model) {
        OrderEntity toSave = convertModelToEntity(model);
        OrderEntity saved = orderRepository.save(toSave);

        // Sadece oluşturma işlemi için
        observers.forEach(o -> o.onOrderCreated(saved));
        return convertEntityToModel(saved);
    }

    /**
     * Remove an order by ID.
     */
    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
        // İsterseniz burada da bir onOrderDeleted(id) çağrısı yapabilirsiniz
    }

    /**
     * Add an item to the order, then return the updated model.
     */
    public Order addItemToOrder(Long orderId, Long itemId) {
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        entity.getItemIds().add(itemId);
        OrderEntity saved = orderRepository.save(entity);

        return convertEntityToModel(saved);
    }

    /**
     * Remove an item from the order, then return the updated model.
     */
    public Order removeItemFromOrder(Long orderId, Long itemId) {
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));

        boolean removed = entity.getItemIds().remove(itemId);
        if (!removed) {
            throw new RuntimeException("Item not found in order: " + itemId);
        }
        OrderEntity saved = orderRepository.save(entity);

        return convertEntityToModel(saved);
    }

    /**
     * Fetch just the raw list of item IDs.
     */
    public List<Long> getOrderItems(Long orderId) {
        OrderEntity entity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + orderId));
        return entity.getItemIds();
    }

    // ────────────────────────────────────────────────────────────────────────────
    // Converter methods
    // ────────────────────────────────────────────────────────────────────────────

    /**
     * Turn your JPA entity into your API model.
     */
    private Order convertEntityToModel(OrderEntity entity) {
        Order model = new Order();
        model.setId(entity.getId());
        model.setCustomerId(entity.getCustomerId());
        model.setItemIds(List.copyOf(entity.getItemIds()));
        model.setStatus(entity.getStatus());
        model.setOrderDate(entity.getOrderDate());
        model.setTotalAmount(entity.getTotalAmount());
        // … diğer alanlar …
        return model;
    }

    /**
     * Turn your API model into a JPA entity.
     * We delegate to your existing factory so all invariants are applied.
     */
    private OrderEntity convertModelToEntity(Order model) {
        OrderEntity entity = OrderFactory.createOrder(model);
        if (model.getId() != null) {
            entity.setId(model.getId());
        }
        return entity;
    }
}
