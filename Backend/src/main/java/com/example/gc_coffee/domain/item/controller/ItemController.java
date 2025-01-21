package com.example.gc_coffee.domain.item.controller;

import com.example.gc_coffee.domain.item.dto.ItemDescriptionDto;
import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.standard.dto.PageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Tag(name = "상품", description = "상품 API")
public class ItemController {
    private final ItemService itemService;

    // 전체 및 검색하여 조회
    @Operation(summary = "상품 조회", description = "단일 상품을 조회합니다.")
    @GetMapping("/{item_id}")
    @Transactional(readOnly = true)
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

    @Operation(summary = "상품 조회", description = "검색을 통해서 상품을 조회합니다.")
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity<PageDto<ItemDto>> items(
            @RequestParam(defaultValue = "") String searchKeyword,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "4") int pageSize
    ) {
        return ResponseEntity.ok()
                .body(
                new PageDto<>(
                        itemService.findByPaged(searchKeyword, page, pageSize)
                                .map(ItemDto::new)
                )
        );
    }
    // 단건 상세내용 조회 (클릭 시 보이는 화면)


    // 상품 등록 정보
    record ItemReqBody (
            @NotBlank Category category,
            @NotBlank String itemName,
            @NotBlank int itemPrice,
            String itemImage,
            @NotBlank int itemQuantity,
            String itemDescription
    ) {
    }

    // 상품 등록
    @Operation(summary = "상품 등록", description = "상품을 등록합니다.")
    @PostMapping
    public ResponseEntity<ItemDescriptionDto> addItem(
            @RequestBody ItemReqBody reqBody
    ) {
        Item item = itemService.addItem(
                reqBody.category,
                reqBody.itemName,
                reqBody.itemPrice,
                reqBody.itemImage,
                reqBody.itemQuantity,
                reqBody.itemDescription
        );

        return ResponseEntity.ok()
                .body(
                        new ItemDescriptionDto(item)
                );

    }

    // 상품 수정
    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    @PatchMapping("/{item_id}")
    @Transactional
    public ResponseEntity<ItemDescriptionDto> modifyItem(
            @PathVariable("item_id") long itemId,
            @RequestBody ItemReqBody reqBody
    ) {
        Item item = itemService.modify(
                itemId,
                reqBody.category,
                reqBody.itemName,
                reqBody.itemPrice,
                reqBody.itemImage,
                reqBody.itemQuantity,
                reqBody.itemDescription
        );

        return ResponseEntity.ok()
                .body(
                        new ItemDescriptionDto(item)
                );
    }

    // 상품 갯수
    @Operation(summary = "상품 갯수", description = "상품 갯수를 조회합니다.")
    @GetMapping("/count")
    public long itemCount() {
        return itemService.count();
    }

}
