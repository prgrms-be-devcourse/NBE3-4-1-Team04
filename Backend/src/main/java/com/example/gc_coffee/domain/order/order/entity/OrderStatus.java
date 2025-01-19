package com.example.gc_coffee.domain.order.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    ORDERED("주문완료"),
    COMPLETED("처리완료"),
    CANCELED("주문취소");

    private final String description;
} 