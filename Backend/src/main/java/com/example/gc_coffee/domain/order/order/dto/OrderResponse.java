package com.example.gc_coffee.domain.order.order.dto;

import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String email;
    private String address;
    private int orderPrice;
    private OrderStatus orderStatus;

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(String.valueOf(order.getOrderNumber()))
                .email(order.getEmail())
                .address(order.getAddress())
                .orderPrice(order.getOrderPrice())
                .orderStatus(order.getOrderStatus())
                .build();
    }
} 