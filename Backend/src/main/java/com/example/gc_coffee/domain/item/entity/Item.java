package com.example.gc_coffee.domain.item.entity;

import com.example.gc_coffee.domain.cart.entity.Cart;
import com.example.gc_coffee.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "items")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Item extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Column(name = "item_id")
    private Long id;

    private String itemName;

    private int itemPrice;

    private String itemImage;

    private Category category;

    private int quantity;

    // todo: 별도 관리해야할지 고민
    private String itemDescription;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}