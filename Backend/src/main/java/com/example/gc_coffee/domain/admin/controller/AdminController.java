package com.example.gc_coffee.domain.admin.controller;

import com.example.gc_coffee.domain.admin.service.AdminService;
import com.example.gc_coffee.domain.item.dto.ItemDescriptionDto;
import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
    @Operation(summary = "상품 등록", description = "관리자가 상품을 등록합니다.")
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

    // 상품 수정
    @Operation(summary = "상품 수정", description = "상품을 수정합니다.")
    @PatchMapping("/{item_id}")
    @Transactional
    public ResponseEntity<ItemDescriptionDto> modifyItem(
            @PathVariable("item_id") long itemId,
            @RequestBody AdminItemReqBody reqBody
    ) {
        Item item = adminService.modify(
                itemId,
                reqBody.category,
                reqBody.itemName,
                reqBody.itemPrice,
                reqBody.itemImage,
                reqBody.quantity,
                reqBody.itemDescription
        );

        return ResponseEntity.ok()
                .body(
                        new ItemDescriptionDto(item)
                );
    }

    // 상품 삭제
    @Operation(summary = "상품 삭제", description = "관리자가 상품을 삭제합니다.")
    @PatchMapping("/delete/{item_id}")
    @Transactional
    public ResponseEntity deleteItem(
            @PathVariable("item_id") long itemId
    ) {
        adminService.deleteItem(itemId);

        return ResponseEntity.ok("상품이 삭제되었습니다.");
    }

    @Operation(summary = "전체 주문 조회", description = "이메일로 주문 목록을 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getOrdersById() {
        return ResponseEntity.ok().body(adminService.getAllOrders());
    }

    @Operation(summary = "주문 상태 수정", description = "주문 상태를 수정합니다.")
    @PatchMapping("/status/{order_id}")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable("order_id") Long orderId,
            @RequestParam("order_status") OrderStatus orderStatus
    ) {
        OrderResponse order = adminService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(order);
    }
}

