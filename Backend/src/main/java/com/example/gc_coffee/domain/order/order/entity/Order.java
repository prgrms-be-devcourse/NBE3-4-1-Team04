package com.example.gc_coffee.domain.order.order.entity;

import com.example.gc_coffee.domain.order.orderItem.entity.OrderItem;
import com.example.gc_coffee.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private String email;

    private String address;

    private LocalDateTime orderTime;

    private int orderPrice;

    private String orderNumber;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.linkOrder(this);
    }

    /**
     * 비즈니스 메서드
     */

    public void updateStatus(OrderStatus status) {
        this.orderStatus = status;
    }

    public void calculateOrderPrice() {
        this.orderPrice = orderItems.stream()
                .mapToInt(item -> item.getItem().getItemPrice() * 1)//Todo 여기에 수량 적용 (변수를 받아서 적용)
                .sum();
    }

    public void cancel() {
        this.orderStatus = OrderStatus.CANCELED;
    }
}