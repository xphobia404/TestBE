package org.TestOBS.TestBE.service;

import java.util.List;
import java.util.Optional;

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

    public Page<Item> getItemsWithPagination(int page, int size) {
        return itemRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Item> editItem(Long id, Item newItem) {
        return itemRepository.findById(id).map(item -> {
            item.setName(newItem.getName());
            item.setPrice(newItem.getPrice());
            return itemRepository.save(item);
        });
    }

    public Item getItemById(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }
}
