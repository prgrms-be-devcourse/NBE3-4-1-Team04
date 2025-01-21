package com.example.gc_coffee.domain.order.orderItem.entity;

import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "order_items")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int quantity;

    /**
     * 연관관계 편의 메서드
     */
    public void linkOrder(Order order) {
        this.order = order;
    }
} 