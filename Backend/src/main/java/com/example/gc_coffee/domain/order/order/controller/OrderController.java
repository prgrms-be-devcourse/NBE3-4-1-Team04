package com.example.gc_coffee.domain.order.order.controller;

import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
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
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest orderRequest
    ) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @Operation(summary = "id로 주문 조회", description = "이메일로 주문 목록을 조회합니다.")
    @GetMapping("/id/{order_id}")
    public ResponseEntity<OrderResponse> getOrdersById(
            @PathVariable("order_id") long orderId
    ) {
        return ResponseEntity.ok().body(orderService.getOrderById(orderId));
    }

    @Operation(summary = "전체 주문 조회", description = "이메일로 주문 목록을 조회합니다.")
    @GetMapping("/all")
    public ResponseEntity<List<OrderResponse>> getOrdersById() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @Operation(summary = "이메일로 주문 조회", description = "이메일로 주문 목록을 조회합니다.")
    @GetMapping("/{email}")
    public ResponseEntity<List<OrderResponse>> getOrdersByEmail(
            @PathVariable String email
    ) {
        return ResponseEntity.ok().body(orderService.findOrdersByEmail(email));
    }

    @Operation(summary = "주문 번호로 주문 조회", description = "주문 번호로 주문을 조회합니다.")
    @GetMapping("/number/{order_number}")
    public ResponseEntity<OrderResponse> getOrderByNumber(
            @PathVariable("order_number") String orderNumber
    ) {
        return ResponseEntity.ok().body(orderService.findOrderByOrderNumber(orderNumber));
    }

    @Operation(summary = "이메일로 주문 개수 조회", description = "이메일로 주문 개수 조회합니다.")
    @GetMapping("/count/{email}")
    public ResponseEntity<Long> getOrderCountByEmail(
            @PathVariable("email") String email
    ) {
        return ResponseEntity.ok().body(orderService.findOrderCountByEmail(email));
    }

    @Operation(summary = "주문 완료 상태 설정", description = "주문 완료 상태로 수정합니다.")
    @PutMapping("/complete/{orderNumber}")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable("orderNumber") String orderNumber
    ) {
        OrderResponse order = orderService.updateOrderStatus(orderNumber);
        return ResponseEntity.ok(order);
    }

    @Operation(summary = "주문 취소", description = "주문을 취소합니다.")
    @PutMapping("/cancel/{orderNumber}")
    public ResponseEntity<OrderResponse> cancelOrder(
            @PathVariable String orderNumber
    ) {
        OrderResponse order = orderService.cancelOrder(orderNumber);
        return ResponseEntity.ok().body(order);
    }
} 