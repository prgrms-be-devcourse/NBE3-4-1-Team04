package com.example.gc_coffee.domain.order.order.dto;

import com.example.gc_coffee.domain.item.dto.ItemDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    private String email;
    private String address;
    private List<ItemDto> items;

    public static OrderRequest of(String email, String address, List<ItemDto> items) {
        return OrderRequest.builder()
                .email(email)
                .address(address)
                .items(items)
                .build();
    }
}