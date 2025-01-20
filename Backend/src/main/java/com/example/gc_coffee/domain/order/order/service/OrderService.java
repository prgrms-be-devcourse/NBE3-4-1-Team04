package com.example.gc_coffee.domain.order.order.service;

import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.order.repository.OrderRepository;
import com.example.gc_coffee.domain.order.orderItem.entity.OrderItem;
import com.example.gc_coffee.domain.order.orderItem.repository.OrderItemRepository;
import com.example.gc_coffee.global.exceptions.BusinessException;
import com.example.gc_coffee.global.exceptions.constant.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemService itemService;

    public OrderService(OrderRepository orderRepository, OrderItemRepository orderItemRepository, ItemService itemService) {
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.itemService = itemService;
    }

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

        orderRepository.save(order);

        orderRepository.flush();

        orderRequest.getItems().forEach(itemRequest -> {
            Item item = itemService.findById(itemRequest.getId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));

            OrderItem orderItem = OrderItem.builder()
                    .item(item)
                    .quantity(1)
                    .price(itemRequest.getItemPrice())
                    .build();

            order.addOrderItem(orderItem);
        });

        orderItemRepository.saveAll(order.getOrderItems());

        order.calculateOrderPrice();

        return OrderResponse.of(orderRepository.save(order));
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        return OrderResponse.of(order);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(OrderResponse::of)
                .collect(Collectors.toList());
    }

    public void deleteOrder(Long orderId) {
        if (!orderRepository.existsById(orderId)) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ORDER);
        }

        orderItemRepository.deleteAllByOrderId(orderId);
        orderRepository.deleteById(orderId);
    }

    public List<OrderResponse> findOrdersByEmail(String email) {
        List<Order> orders = orderRepository.findAllByEmail(email);
        if (orders.isEmpty()) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ORDER);
        }
        return orders.stream()
                .map(OrderResponse::of)
                .collect(Collectors.toList());
    }

    public OrderResponse findOrderByOrderNumber(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        return OrderResponse.of(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        order.updateStatus(OrderStatus.COMPLETED);
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

    public Optional<Order> fineLatest() {
        return orderRepository.findFirstByOrderByIdDesc();
    }

    private String generateOrderNumber(LocalDateTime dateTime) {
        String datePart = dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyMMdd"));
        String uniquePart = UUID.randomUUID().toString().substring(0, 6);
        return "ORDER-" + datePart + uniquePart;
    }
}
