package com.example.gc_coffee.domain.item.controller;

import com.example.gc_coffee.domain.item.dto.ItemDescriptionDto;
import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.standard.dto.PageDto;
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
    @PatchMapping("/{item_id}")
    @Transactional
    public ResponseEntity<ItemDescriptionDto> modifyItem(
            @PathVariable long item_id,
            @RequestBody ItemReqBody reqBody
    ) {
        Item item = itemService.findById(item_id).get();

        itemService.modify(
                item,
                reqBody.category,
                reqBody.itemName,
                reqBody.itemPrice,
                reqBody.itemImage,
                reqBody.itemQuantity,
                reqBody.itemDescription
        );

        itemService.flush();

        return ResponseEntity.ok()
                .body(
                        new ItemDescriptionDto(item)
                );
    }

    // 상품 삭제
    @DeleteMapping("/{item_id}")
    @Transactional
    public ResponseEntity<String> modifyItem(@PathVariable long item_id) {

        Item item = itemService.findById(item_id).get();

        itemService.delete(item);

        itemService.flush();

        return ResponseEntity.ok("삭제가 완료되었습니다");
    }



    // 상품 갯수
    @GetMapping("/count")
    public long itemCount() {
        return itemService.count();
    }

}
