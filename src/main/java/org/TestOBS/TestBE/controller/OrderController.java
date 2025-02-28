package org.TestOBS.TestBE.controller;

import java.util.List;
import java.util.Optional;

import org.TestOBS.TestBE.model.Item;
import org.TestOBS.TestBE.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private ItemService itemService;

    @GetMapping(value = "/list")
    public List<Item> list() {
        return itemService.getAllItems();
    }

    @GetMapping(value = "/list/page")
    public Page<Item> listPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return itemService.getItemsWithPagination(page, size);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Item> saveItem(@RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);
        return ResponseEntity.ok(savedItem);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item newItem) {
        Optional<Item> updatedItem = itemService.editItem(id, newItem);
        return updatedItem.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        try {
            itemService.deleteItem(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
