package com.example.gc_coffee.domain.item.dto;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemDescriptionDto {
    private Long id;
    private String itemName;
    private Category category;
    private int itemPrice;
    private String itemImage;
    private int quantity;
    private String itemDescription;
    private boolean isDeleted;

    public ItemDescriptionDto(Item item) {
        this.id = item.getId();
        this.itemName = item.getItemName();
        this.category = item.getCategory();
        this.itemPrice = item.getItemPrice();
        this.itemImage = item.getItemImage();
        this.quantity = item.getStockQuantity();
        this.itemDescription = item.getItemDescription();
        this.isDeleted = item.isDeleted();
    }

    @Override
    public String toString() {
        return "ItemDescriptionDto{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", category=" + category +
                ", itemPrice=" + itemPrice +
                ", itemImage='" + itemImage + '\'' +
                ", quantity=" + quantity +
                ", itemDescription='" + itemDescription + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
