package org.TestOBS.TestBE.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.TestOBS.TestBE.model.Inventory;
import org.TestOBS.TestBE.model.Item;
import org.TestOBS.TestBE.repository.InventoryRepository;
import org.TestOBS.TestBE.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<Map<String, Object>> getAllInventories() {
        return inventoryRepository.findAll().stream()
            .map(inventory -> {
                Map<String, Object> result = new HashMap<>();
                result.put("idInventory", inventory.getIdInventory());
                result.put("idItem", inventory.getItem().getIdItem());
                result.put("quantity", inventory.getQuantity());
                result.put("type", inventory.getType());
                return result;
            })
            .collect(Collectors.toList());
    }

    public Page<Inventory> getInventoryWithPagination(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size));
    }

    @Transactional
    public Optional<Inventory> editInventory(Long id, Inventory newInventory) {
        return inventoryRepository.findById(id).map(existingInventory -> {

            existingInventory.setQuantity(newInventory.getQuantity());
            existingInventory.setType(newInventory.getType());

            updateStock(existingInventory);

            return inventoryRepository.save(existingInventory);
        });
    }

    private void updateStock(Inventory inventory) {
        Item item = itemRepository.findById(inventory.getItem().getIdItem())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        if ("T".equals(inventory.getType())) { // Top Up
            item.setItemStock(item.getItemStock() + inventory.getQuantity());
        } else if ("W".equals(inventory.getType())) { // Withdrawal
            if (item.getItemStock() < inventory.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }
            item.setItemStock(item.getItemStock() - inventory.getQuantity());
        }
        itemRepository.save(item);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    @Transactional
    public Map<String, Object> saveInventory(Inventory inventory) {

    Item item = itemRepository.findById(inventory.getItem().getIdItem())
        .orElseThrow(() -> new RuntimeException("Item not found"));

    inventory.setQuantity(item.getItemStock());
    inventory.setType("T");

    Inventory savedInventory = inventoryRepository.save(inventory);

    Map<String, Object> result = new HashMap<>();
    result.put("idInventory", savedInventory.getIdInventory());
    result.put("idItem", savedInventory.getItem().getIdItem());
    result.put("nameItem", savedInventory.getItem().getItemName());
    result.put("quantity", savedInventory.getQuantity());
    result.put("type", savedInventory.getType());

    return result;
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
