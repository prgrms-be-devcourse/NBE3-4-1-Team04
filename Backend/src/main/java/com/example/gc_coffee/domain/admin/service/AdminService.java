package com.example.gc_coffee.domain.admin.service;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // RestController  -> RequiredArgsConstructor으로 변경
public class AdminService {

    private final ItemService itemService;

    // 상품 등록
    public Item addItem(Category category, String itemName, int itemPrice, String itemImage, int quantity, String description) {
        return itemService.addItem(category, itemName, itemPrice, itemImage, quantity, description);
    }
}
