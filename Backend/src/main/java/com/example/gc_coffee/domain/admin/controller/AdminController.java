package com.example.gc_coffee.domain.admin.controller;

import com.example.gc_coffee.domain.admin.service.AdminService;
import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.item.entity.Item;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    // 상품 등록 API
    @PostMapping("/items")
    public ResponseEntity<Item> addItem(@RequestBody ItemDto itemDto) {
        Item item = adminService.addItem(
                itemDto.getCategory(),
                itemDto.getItemName(),
                itemDto.getItemPrice(),
                itemDto.getItemImage(),
                itemDto.getQuantity(),
                itemDto.getItemDescription()
        );
        return ResponseEntity.ok(item);
        }

}

