package com.example.gc_coffee.domain.order.order.service;

import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.order.repository.OrderRepository;
import com.example.gc_coffee.domain.order.orderItem.entity.OrderItem;
import com.example.gc_coffee.global.exceptions.BusinessException;
import com.example.gc_coffee.global.exceptions.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    @Transactional
    public OrderResponse createOrder(OrderRequest orderRequest) {
        LocalDateTime now = LocalDateTime.now();
        String orderNumber = generateOrderNumber(now);

        Order order = Order.builder()
                .email(orderRequest.getEmail())
                .address(orderRequest.getAddress())
                .orderTime(now)
                .orderNumber(orderNumber)
                .orderStatus(OrderStatus.ORDERED)
                .build();

        orderRequest.getItems().forEach(item -> {
            Item itemEntity = itemService.findById(item.getId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

            OrderItem orderItem = OrderItem.builder()
                    .item(itemEntity)
                    .quantity(item.getQuantity())
                    .price(item.getItemPrice())
                    .build();
            order.addOrderItem(orderItem);
        });

        order.calculateOrderPrice(); // 전체 주문 금액 계산
        return OrderResponse.of(orderRepository.save(order));
    }

    private String generateOrderNumber(LocalDateTime dateTime) {
        // 날짜 형식: YYMMDD
        String datePart = dateTime.format(DateTimeFormatter.ofPattern("yyMMdd"));
        // UUID의 처음 6자리만 사용
        String uniquePart = UUID.randomUUID().toString().substring(0, 6);
        return datePart + uniquePart;
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> findAllByEmail(String email) {
        List<Order> orders = orderRepository.findAllByEmail(email);

        if (orders.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ORDER);
        }

        return orders.stream()
                .map(OrderResponse::of)
                .toList();
    }

    public OrderResponse findByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() ->new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(String orderNumber, OrderStatus status) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        order.updateStatus(status);

        return OrderResponse.of(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse cancelOrder(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        order.cancel();
        return OrderResponse.of(orderRepository.save(order));
    }

    public Long count() {
        return orderRepository.count();
    }
} 