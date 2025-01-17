package com.example.gc_coffee.domain.order.order.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Tag(name = "주문", description = "주문 API")
public class OrderController {
//    // 다건 및 검색 조회
//    @GetMapping
//    public ResponseEntity<PageDto<ItemDto>> items(
//            @RequestParam(defaultValue = "itemName") String searchKeywordType,
//            @RequestParam(defaultValue = "") String searchKeyword,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "4") int pageSize
//    ) {
//        return ResponseEntity.ok()
//                .body(
//                        new PageDto<>(
//                                orderService.findByPaged(searchKeywordType, searchKeyword, page, pageSize)
//                                        .map(ItemDto::new)
//                        )
//                );
//    }
}
