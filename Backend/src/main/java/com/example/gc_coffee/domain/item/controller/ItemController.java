package com.example.gc_coffee.domain.item.controller;

import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.item.dto.ItemDescriptionDto;
import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.standard.dto.PageDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class ItemController {
    private final ItemService itemService;

    // 다건 및 검색 조회
    @GetMapping
    public ResponseEntity<PageDto<ItemDto>> orderItems(
            @RequestParam(defaultValue = "itemName") String searchKeywordType,
            @RequestParam(defaultValue = "") String searchKeyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int pageSize
    ) {
        return ResponseEntity.ok()
                .body(
                new PageDto<>(
                        itemService.findByPaged(searchKeywordType, searchKeyword, page, pageSize)
                                .map(ItemDto::new)
                )
        );
    }

    // 단건 상세내용 조회 (클릭 시 보이는 화면)
    @GetMapping("/{item_id}")
    public ResponseEntity<ItemDescriptionDto> item(
            @PathVariable long item_id
    ) {
        return ResponseEntity.ok()
                .body(
                        itemService.findById(item_id)
                        .map(ItemDescriptionDto::new)
                        .orElseThrow()
                );

    }

    // 상품 갯수
    @GetMapping("/count")
    public long itemCount() {
        return itemService.count();
    }
}
