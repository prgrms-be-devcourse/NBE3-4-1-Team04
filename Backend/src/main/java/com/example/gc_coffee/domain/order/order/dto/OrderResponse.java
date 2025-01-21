package com.example.gc_coffee.domain.order.order.dto;

import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.orderItem.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;

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
    private Map<ItemDto, Integer> items;

    public static OrderResponse of(Order order) {
        Map<ItemDto, Integer> items = order.getOrderItems().stream()
                .collect(Collectors.toMap(
                        orderItem -> new ItemDto(orderItem.getItem()), // ItemDto 변환
                        OrderItem::getCount // 수량
                ));


        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(String.valueOf(order.getOrderNumber()))
                .email(order.getEmail())
                .address(order.getAddress())
                .orderPrice(order.getOrderPrice())
                .orderStatus(order.getOrderStatus())
                .items(items)
                .build();
    }
} 