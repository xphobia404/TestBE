package org.TestOBS.TestBE.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.TestOBS.TestBE.model.Item;
import org.TestOBS.TestBE.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public List<Map<String, Object>> getItemsWithoutStock() {
        return itemRepository.findAll().stream()
            .map(item -> {
                Map<String, Object> result = new HashMap<>();
                result.put("idItem", item.getIdItem());
                result.put("itemName", item.getItemName());
                result.put("itemPrice", item.getItemPrice());
                return result;
            })
            .collect(Collectors.toList());
    }

    public Page<Item> getItemsWithPagination(int page, int size) {
        return itemRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Item> editItem(Long id, Item newItem) {
        return itemRepository.findById(id).map(item -> {
            item.setItemName(newItem.getItemName());
            item.setItemPrice(newItem.getItemPrice());
            item.setItemStock(newItem.getItemStock());
            return itemRepository.save(item);
        });
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
