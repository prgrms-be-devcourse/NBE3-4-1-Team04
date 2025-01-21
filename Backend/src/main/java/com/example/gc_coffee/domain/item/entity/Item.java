package com.example.gc_coffee.domain.item.entity;

import com.example.gc_coffee.domain.cart.entity.Cart;
import com.example.gc_coffee.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Column(name = "item_id")
    private Long id;

    private String itemName; // 상품명

    private int itemPrice; // 상품 가격

    private String itemImage; // 상품 이미지 URL

    @Enumerated(value = EnumType.STRING)
    private Category category; // 상품 카테고리

    private int quantity; // 상품 재고 수량

    private boolean isDeleted; // 상품 삭제 여부

    // Todo: 별도 관리해야할지 고민
    private String itemDescription; // 상품 설명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart; // 장바구니와의 관계

    /**
     * 비즈니스 메서드
     */

    public void removeItem(){
        this.isDeleted = true;
    }
}