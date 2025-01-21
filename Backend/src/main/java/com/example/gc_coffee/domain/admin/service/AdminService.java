package com.example.gc_coffee.domain.admin.service;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final ItemService itemService;
    private final OrderService orderService;

    // 상품 등록
    public Item addItem(Category category, String itemName, int itemPrice, String itemImage, int quantity, String description) {
        return itemService.addItem(category, itemName, itemPrice, itemImage, quantity, description);
    }

    public void deleteItem(Long orderId) {
        itemService.delete(orderId);
    }

}
