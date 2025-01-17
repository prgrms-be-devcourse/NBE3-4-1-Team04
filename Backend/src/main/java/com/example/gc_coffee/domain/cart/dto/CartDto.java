package com.example.gc_coffee.domain.cart.dto;

import com.example.gc_coffee.domain.item.entity.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CartDto {
    private Long id;
    private int quantity;
    private List<Item> items;
}
