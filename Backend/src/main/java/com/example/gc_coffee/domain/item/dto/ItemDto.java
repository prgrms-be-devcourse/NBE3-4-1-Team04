package com.example.gc_coffee.domain.item.dto;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Long id;

    @JsonIgnore
    private LocalDateTime createDate;

    @JsonIgnore
    private LocalDateTime modifyDate;
    private String itemName;   // 상품명
    private Category category; // 상품 카테고리
    private int itemPrice;     // 상품 가격
    private String itemImage;  // 상품 이미지 URL
    private int quantity;      // 재고 수량
    private String itemDescription; // 상품 설명
    private boolean isDeleted; // 상품 삭제

    public ItemDto(Item item) {
        this.id = item.getId();
        this.createDate = item.getCreateDate();
        this.modifyDate = item.getModifyDate();
        this.itemName = item.getItemName();
        this.category = item.getCategory();
        this.itemPrice = item.getItemPrice();
        this.itemImage = item.getItemImage();
        this.quantity = item.getStockQuantity();
        this.itemDescription = item.getItemDescription();
    }

    @Override
    public String toString() {
        return "ItemDto" + id + "{" +
                ", itemName='" + itemName + '\'' +
                ", itemPrice=" + itemPrice +
                ", itemDescription='" + itemDescription + '\'' +
                '}';
    }
}
