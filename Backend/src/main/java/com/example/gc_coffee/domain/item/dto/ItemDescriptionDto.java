package com.example.gc_coffee.domain.item.dto;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemDescriptionDto {
    private Long id;           // 상품 ID
    private String itemName;   // 상품명
    private Category category; // 상품 카테고리
    private int itemPrice;     // 상품 가격
    private String itemImage;  // 상품 이미지 URL
    private int quantity;      // 재고 수량
    private String itemDescription; // 상품 설명

    @JsonIgnore
    private boolean isDeleted; // 상품 삭제 여부

    // 엔티티에서 DTO로 변환하는 생성자
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
        return String.format("Item{id=%d, itemName='%s', itemPrice=%d, quantity=%d, description='%s'}",
                id, itemName, itemPrice, quantity, itemDescription);
    }
}
