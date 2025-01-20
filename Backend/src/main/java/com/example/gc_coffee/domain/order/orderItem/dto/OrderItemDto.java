package com.example.gc_coffee.domain.order.orderItem.dto;

import com.example.gc_coffee.domain.item.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {
    private Long itemId;
    private String itemImage;
    private int quantity;
    private int price;
    private Category category;

}
