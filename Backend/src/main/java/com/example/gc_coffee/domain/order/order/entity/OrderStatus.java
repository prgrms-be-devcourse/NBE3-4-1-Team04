package com.example.gc_coffee.domain.order.order.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    YET,
    DONE,
    CANCELED

}
