package com.example.gc_coffee.domain.cart.entity;


import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cart")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본 키 자동 생성
    @Column(name = "cart_id")
    private Long id;

    private int quantity;

    @OneToMany(mappedBy = "cart", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<Item> items = new ArrayList<>();
}
