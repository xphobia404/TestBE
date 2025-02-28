package org.TestOBS.TestBE.controller;

import java.util.List;
import java.util.Optional;

import org.TestOBS.TestBE.model.Inventory;
import org.TestOBS.TestBE.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping(value = "/list")
    public List<Inventory> list() {
        return inventoryService.getAllInventories();
    }

    @GetMapping(value = "/list/page")
    public Page<Inventory> listPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return inventoryService.getInventoryWithPagination(page, size);
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Inventory> saveInventory(@RequestBody Inventory item) {
        Inventory savedItem = inventoryService.saveInventory(item);
        return ResponseEntity.ok(savedItem);
    }

    // @PutMapping(value = "/update/{id}")
    // public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory newInventory) {
    //     Optional<Inventory> updateInventory = inventoryService.editInventory(id, newInventory);
    //     return updateInventory.map(ResponseEntity::ok)
    //             .orElseGet(() -> ResponseEntity.notFound().build());
    // }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        try {
            inventoryService.deleteInventory(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
