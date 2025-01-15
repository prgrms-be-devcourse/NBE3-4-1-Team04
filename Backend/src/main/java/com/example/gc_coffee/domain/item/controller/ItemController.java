package com.example.gc_coffee.domain.item.controller;

import com.example.gc_coffee.domain.item.ItemService.ItemService;
import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.item.entity.Item;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class ItemController {
    private final ItemService itemService;

    @GetMapping
    public List<ItemDto> items() {
        List<Item> allItems = itemService.findByAll();
        return allItems.stream()
                .map(ItemDto::new)
                .collect(Collectors.toList());
    }
}
