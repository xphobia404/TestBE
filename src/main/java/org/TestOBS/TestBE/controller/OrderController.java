package org.TestOBS.TestBE.controller;

import java.util.List;
import java.util.Map;

import org.TestOBS.TestBE.model.Order;
import org.TestOBS.TestBE.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/list")
    public List<Map<String, Object>> list() {
        return orderService.getAllOrders();
    }

    @GetMapping(value = "/list/page")
    public Page<Order> listPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return orderService.getOrderWithPagination(page, size);
    }

    @PostMapping("/save")
    public ResponseEntity<Map<String, Object>> saveOrder(@RequestBody Order order) {
        Map<String, Object> response = orderService.saveOrder(order);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updateOrder(@PathVariable Long id, @RequestBody Order newOrder) {
        Map<String, Object> response = orderService.updateOrder(id, newOrder);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        try {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
