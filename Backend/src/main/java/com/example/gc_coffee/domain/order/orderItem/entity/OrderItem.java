package com.example.gc_coffee.domain.order.orderItem.entity;

import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter
@Setter
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

    private int count;

    //==생성 메서드==//
    public static OrderItem createOrderItem(Item item, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    public void cancel() {
        getItem().addStock(count); //재고 수량을 원상 복구
    }

    /**
     * 연관관계 편의 메서드
     */
    public void linkOrder(Order order) {
        this.order = order;
    }

    public void unlinkOrder() {
        if (this.order != null) {
            this.order.getOrderItems().remove(this);
            this.order = null;
        }
    }
} 