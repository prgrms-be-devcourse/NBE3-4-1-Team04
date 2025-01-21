package com.example.gc_coffee.domain.admin.service;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    //상품 수정
    public Item modify(long itemId, Category category, String itemName, int itemPrice, String itemImage, int itemQuantity, String description) {
        return itemService.modify(itemId, category, itemName, itemPrice, itemImage, itemQuantity, description);
    }

    //상품 삭제
    public void deleteItem(Long orderId) {
        itemService.delete(orderId);
    }

    //주문 상태 수정
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        return orderService.updateOrderStatus(orderId, orderStatus);
    }

    //전체 주문 조회
    @Transactional(readOnly = true)
    public List<OrderResponse> getAllOrders() {
        return orderService.getAllOrders();
    }
}
