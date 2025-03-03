package org.TestOBS.TestBE.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.TestOBS.TestBE.model.Inventory;
import org.TestOBS.TestBE.model.Item;
import org.TestOBS.TestBE.model.Order;
import org.TestOBS.TestBE.repository.InventoryRepository;
import org.TestOBS.TestBE.repository.ItemRepository;
import org.TestOBS.TestBE.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Map<String, Object>> getAllOrders() {
        return orderRepository.findAll().stream()
            .map(order -> {
                Map<String, Object> result = new HashMap<>();
                result.put("idOrder", order.getIdOrder());
                result.put("idItem", order.getItem().getIdItem());
                result.put("quantity", order.getQty());
                result.put("price", order.getItem().getItemPrice());
                return result;
            })
            .collect(Collectors.toList());
    }

    public Page<Order> getOrderWithPagination(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Map<String, Object> saveOrder(Order order) {

        Item item = itemRepository.findById(order.getItem().getIdItem())
            .orElseThrow(() -> new RuntimeException("Item not found"));

        Inventory inventory = inventoryRepository.findById(order.getInventory().getIdInventory())
            .orElseThrow(() -> new RuntimeException("Inventory not found"));

        if (inventory.getQuantity() < order.getQty()) {
            throw new RuntimeException("Insufficient stock");
        }

        inventory.setQuantity(inventory.getQuantity() - order.getQty());
        inventoryRepository.save(inventory);

        Order savedOrder = orderRepository.save(order);

        Map<String, Object> result = new HashMap<>();
        result.put("idOrder", savedOrder.getIdOrder());
        result.put("idItem", item.getIdItem());
        result.put("nameItem", item.getItemName());
        result.put("quantity", order.getQty());
        result.put("price", item.getItemPrice());

        return result;
    }

    @Transactional
    public Map<String, Object> updateOrder(Long id, Order newOrder) {
        Order existingOrder = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        existingOrder.setItem(newOrder.getItem());
        existingOrder.setInventory(newOrder.getInventory());

        Order updatedOrder = orderRepository.save(existingOrder);

        Map<String, Object> result = new HashMap<>();
        result.put("idOrder", updatedOrder.getIdOrder());
        result.put("idItem", updatedOrder.getItem().getIdItem());
        result.put("quantity", updatedOrder.getInventory().getQuantity());
        result.put("price", updatedOrder.getItem().getItemPrice());

        return result;
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
