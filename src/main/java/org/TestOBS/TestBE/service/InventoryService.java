package org.TestOBS.TestBE.service;

import java.util.List;

import org.TestOBS.TestBE.exception.Exception;
import org.TestOBS.TestBE.model.Inventory;
import org.TestOBS.TestBE.model.Item;
import org.TestOBS.TestBE.repository.InventoryRepository;
import org.TestOBS.TestBE.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    public List<Inventory> getAllInventories() {
        return inventoryRepository.findAll();
    }

    public Page<Inventory> getInventoryWithPagination(int page, int size) {
        return inventoryRepository.findAll(PageRequest.of(page, size));
    }

    public void updateStock(Inventory inventory) {
        Item item = itemRepository.findById(inventory.getIdInventory()).orElseThrow();

        if (inventory.getType().equals("T")) {
            item.setStock(item.getStock() + inventory.getQuantity());
        } else if (inventory.getType().equals("W")) {
            if (item.getStock() < inventory.getQuantity()) {
                throw new Exception("Insufficient stock");
            }
            item.setStock(item.getStock() - inventory.getQuantity());
        }
        itemRepository.save(item);
    }

    public Inventory getInventoryById(Long id) {
        return inventoryRepository.findById(id).orElse(null);
    }

    public Inventory saveInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    public void deleteInventory(Long id) {
        inventoryRepository.deleteById(id);
    }
}
