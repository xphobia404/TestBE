package org.TestOBS.TestBE.service;

import java.util.List;
import java.util.Optional;

import org.TestOBS.TestBE.model.Order;
import org.TestOBS.TestBE.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllInventories() {
        return orderRepository.findAll();
    }

    public Page<Order> getOrderWithPagination(int page, int size) {
        return orderRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Order> editOrder(Long id, Order newOrder) {
        return orderRepository.findById(id).map(Order -> {
            Order.setItem(newOrder.getItem());
            Order.setQuantity(newOrder.getQuantity());
            Order.setPrice(newOrder.getPrice());
            return orderRepository.save(Order);
        });
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Order saveOrder(Order Order) {
        return orderRepository.save(Order);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}
