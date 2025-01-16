package com.example.gc_coffee.domain.item.controller;

import com.example.gc_coffee.domain.item.ItemService.ItemService;
import com.example.gc_coffee.domain.item.dto.ItemDescriptionDto;
import com.example.gc_coffee.domain.item.dto.ItemDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class ItemController {
    private final ItemService itemService;

    // 다건 조회
    @GetMapping
    public List<ItemDto> items(
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "4") int pageSize
    ) {
        return itemService.findAllByOrderByIdDesc()
                .stream()
                .map(ItemDto::new)
                .toList();
        // todo: 페이지에 보이도록
        // todo: 검색해서 조회할 수 있는 코드 추가
    }

    // 단건 조회
    @GetMapping("/{item_id}")
    public ItemDto item(
            @PathVariable long item_id
    ) {

        return itemService.findById(item_id)
                        .map(ItemDto::new)
                        .orElseThrow();
    }

    // 상세 내용 조회 todo: 클릭 시 전체 내용을 보여주도록 해야함
    @GetMapping("/{item_id}/details")
    public ItemDescriptionDto itemDetails(
            @PathVariable long item_id
    ) {
        return itemService.findById(item_id)
                .map(ItemDescriptionDto::new)
                .orElseThrow();
    }
}
