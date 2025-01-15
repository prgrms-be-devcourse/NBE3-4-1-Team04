package com.example.gc_coffee.domain.item.controller;

import com.example.gc_coffee.domain.item.ItemService.ItemService;
import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.standard.dto.PageDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class ItemController {
    private final ItemService itemService;

    // 다건 조회
    @GetMapping
    public PageDto<ItemDto> items(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int pageSize
    ) {
        return new PageDto<>(
                itemService.findByPaged(page, pageSize)
                        .map(ItemDto::new)
        );
    }


}
