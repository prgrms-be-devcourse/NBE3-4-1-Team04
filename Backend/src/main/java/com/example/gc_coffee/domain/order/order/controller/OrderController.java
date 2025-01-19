package com.example.gc_coffee.domain.order.order.controller;

import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Tag(name = "주문 API", description = "주문 관련 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "새로운 주문을 생성합니다.")
    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {
            return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @Operation(summary = "이메일로 주문 조회", description = "이메일로 주문 목록을 조회합니다.")
    @GetMapping("/{email}")
    public ResponseEntity<List<OrderResponse>> getOrdersByEmail(@PathVariable String email) {
            return ResponseEntity.ok().body(orderService.findAllByEmail(email));
    }

    @Operation(summary = "주문 조회", description = "주문 번호로 주문을 조회합니다.")
    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrderByNumber(@PathVariable String orderNumber) {
            return ResponseEntity.ok().body(orderService.findByOrderNumber(orderNumber));
    }

    @Operation(summary = "주문 상태 수정", description = "주문의 상태를 수정합니다.")
    @PutMapping("/{orderNumber}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable String orderNumber,
            @RequestParam OrderStatus status) {
            OrderResponse order = orderService.updateOrderStatus(orderNumber, status);
            return ResponseEntity.ok(order);
    }

    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @PutMapping("/{orderNumber}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable String orderNumber) {
        try {
            OrderResponse order = orderService.cancelOrder(orderNumber);
            return ResponseEntity.ok(order);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
} 