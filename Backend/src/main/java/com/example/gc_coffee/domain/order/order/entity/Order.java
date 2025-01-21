package com.example.gc_coffee.domain.order.order.entity;

import com.example.gc_coffee.domain.order.orderItem.entity.OrderItem;
import com.example.gc_coffee.global.exceptions.BusinessException;
import com.example.gc_coffee.global.exceptions.constant.ErrorCode;
import com.example.gc_coffee.global.jpa.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
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

    private LocalDateTime orderDate;

    private int orderPrice;

    private String orderNumber;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItem> orderItems = new ArrayList<>();

    /**
     * 생성 메서드
     */
    // Order가 연관 관계를 세팅하고 상태랑 주문 시간까지 세팅 / 생성하는 시점에서 변경을 이 메서드에서만 하면된다
    public static Order createOrder(String email, String address, String orderNumber, List<OrderItem> orderItems) {
        Order order = new Order();
        order.setEmail(email);
        order.setAddress(address);
        order.setOrderNumber(orderNumber);

        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDERED); //상태
        order.setOrderDate(LocalDateTime.now()); //현재 시간
        return order;
    }


    /**
     * 연관관계 편의 메서드
     */
    public void addOrderItem(OrderItem orderItem) {
        // 기존 항목 검색
        OrderItem existingOrderItem = this.orderItems.stream()
                .filter(oi -> oi.getItem().equals(orderItem.getItem()))
                .findFirst()
                .orElse(null);

        if (existingOrderItem != null) {
            // 기존 항목이 있을 경우 수량 증가
            existingOrderItem.setCount(existingOrderItem.getCount() + orderItem.getCount());
        } else {
            // 기존 항목이 없을 경우 새 항목 추가
            this.orderItems.add(orderItem);
            orderItem.linkOrder(this);
        }
    }

    /**
     * 비즈니스 메서드
     */

    public void cancel() {
        if (orderStatus == OrderStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.COMPLETED_ORDER);
        }
        this.orderStatus = OrderStatus.CANCELED;
        for (OrderItem orderItem : orderItems) {
            orderItem.cancel(); // 주문된 제품에도 cancel을 각각 날려줌
        }
    }

    public void orderComplete() {
        this.orderStatus = OrderStatus.COMPLETED;
    }

    public void calculateOrderPrice() {
        this.orderPrice = orderItems.stream()
                .mapToInt(oi -> oi.getItem().getItemPrice() * oi.getCount())//Todo 여기에 수량 적용 (변수를 받아서 적용)
                .sum();
    }

}