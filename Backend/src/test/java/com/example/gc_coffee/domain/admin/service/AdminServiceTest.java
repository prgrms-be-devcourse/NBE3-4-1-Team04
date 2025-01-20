package com.example.gc_coffee.domain.admin.service;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTest {

    @Mock
    private ItemService itemService;

    @InjectMocks
    private AdminService adminService;

    @Test
    @DisplayName("관리자, 상품 등록 테스트")
    void addItemTest() {
        // Arrange
        MockitoAnnotations.openMocks(this);
        Category category = Category.COFFEE;
        String itemName = "아메리카노";
        int itemPrice = 4500;
        String itemImage = "/image.png";
        int quantity = 100;
        String description = "신선한 원두로 만든 커피";

        Item mockItem = Item.builder()
                .category(category)
                .itemName(itemName)
                .itemPrice(itemPrice)
                .itemImage(itemImage)
                .quantity(quantity)
                .itemDescription(description)
                .build();

        when(itemService.addItem(category, itemName, itemPrice, itemImage, quantity, description)).thenReturn(mockItem);

        // Act
        Item savedItem = adminService.addItem(category, itemName, itemPrice, itemImage, quantity, description);

        // Assert
        assertNotNull(savedItem);
        assertEquals(itemName, savedItem.getItemName());
        verify(itemService, times(1)).addItem(category, itemName, itemPrice, itemImage, quantity, description);
    }
}