package com.example.gc_coffee.domain.item.dto;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ItemDto {
    private Long id;
    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String itemName;
    private int itemPrice;
    private String itemImage;
    private Category category;
    private int quantity;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.createDate = item.getCreateDate();
        this.modifyDate = item.getModifyDate();
        this.itemName = item.getItemName();
        this.itemPrice = item.getItemPrice();
        this.itemImage = item.getItemImage();
        this.category = item.getCategory();
        this.quantity = item.getQuantity();
    }
}
