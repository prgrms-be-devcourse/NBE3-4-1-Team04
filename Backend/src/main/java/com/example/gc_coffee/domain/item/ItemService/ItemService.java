package com.example.gc_coffee.domain.item.ItemService;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    public Item addItem(Category category, String itemName, int itemPrice, String itemImage, int quantity) {
        Item item = Item.builder()
                .category(category)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemImage(itemImage)
                .quantity(quantity)
                .build();

        return itemRepository.save(item);
    }

    public Long count() {
        return itemRepository.count();
    }

    public List<Item> findByAll() {
        return itemRepository.findAll();
    }

    public Page<Item> findByPaged(int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page - 1, pageSize);
        return itemRepository.findAll(pageRequest);
    }

    public Optional<Item> findById(long itemId) {
        return itemRepository.findById(itemId);
    }
}
