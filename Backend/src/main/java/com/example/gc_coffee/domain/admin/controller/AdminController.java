package com.example.gc_coffee.domain.admin.controller;

import com.example.gc_coffee.domain.admin.service.AdminService;
import com.example.gc_coffee.domain.item.dto.ItemDescriptionDto;
import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "관리자", description = "관리자 API")
public class AdminController {
    private final AdminService adminService;


    // 상품 등록 요청 정보
    record AdminItemReqBody(
            @NotBlank Category category,
            @NotBlank String itemName,
            @NotBlank int itemPrice,
            String itemImage,
            @NotBlank int quantity,
            String itemDescription
    ) {
    }

    // 상품 등록 API
    @PostMapping("/items")
    public ResponseEntity<ItemDescriptionDto> addItem(
            @RequestBody AdminItemReqBody reqBody
    ) {
        // 요청 데이터를 기반으로 Item 생성
        Item item = adminService.addItem(
                reqBody.category,
                reqBody.itemName,
                reqBody.itemPrice,
                reqBody.itemImage,
                reqBody.quantity,
                reqBody.itemDescription
        );

        return ResponseEntity.ok(
                new ItemDescriptionDto(item)
        );
    }
}

