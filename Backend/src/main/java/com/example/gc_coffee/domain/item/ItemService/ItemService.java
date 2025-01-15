package com.example.gc_coffee.domain.item.ItemService;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
