package com.example.gc_coffee.domain.item.dto;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;

    @JsonIgnore
    private LocalDateTime createDate;

    @JsonIgnore
    private LocalDateTime modifyDate;
    private String itemName;
    private Category category;
    private int itemPrice;
    private String itemImage;
    private int quantity;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.createDate = item.getCreateDate();
        this.modifyDate = item.getModifyDate();
        this.itemName = item.getItemName();
        this.category = item.getCategory();
        this.itemPrice = item.getItemPrice();
        this.itemImage = item.getItemImage();
        this.quantity = item.getQuantity();
    }
}
