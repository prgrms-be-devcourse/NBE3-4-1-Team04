package com.example.gc_coffee.domain.order.order.service;

import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.repository.ItemRepository;
import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.order.repository.OrderRepository;
import com.example.gc_coffee.domain.order.orderItem.entity.OrderItem;
import com.example.gc_coffee.domain.order.orderItem.repository.OrderItemRepository;
import com.example.gc_coffee.global.exceptions.BusinessException;
import com.example.gc_coffee.global.exceptions.constant.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;


    public OrderResponse createOrder(OrderRequest orderRequest) {
        // 주문 번호 생성
        String orderNumber = generateOrderNumber(LocalDateTime.now());

        // OrderItem 리스트 생성
        List<OrderItem> orderItemList = createOrderItems(orderRequest);

        Order order = Order.createOrder(
                orderRequest.getEmail(),
                orderRequest.getAddress(),
                orderNumber,
                orderItemList
        );

        // 총 주문 가격 계산
        order.calculateOrderPrice();

        saveOrderAndItems(order);

        return OrderResponse.of(order);
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

    public OrderResponse findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        return OrderResponse.of(order);
    }

    public Long findOrderCountByEmail(String email) {
        return orderRepository.countByEmail(email);
    }

    @Transactional
    public OrderResponse modify(long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));
        order.setOrderStatus(orderStatus);
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

    //테스팅
    public Optional<Order> fineLatest() {
        return orderRepository.findFirstByOrderByIdDesc();
    }

    private void saveOrderAndItems(Order order) {
        orderRepository.save(order);
        orderItemRepository.saveAll(order.getOrderItems());
    }

    private List<OrderItem> createOrderItems(OrderRequest orderRequest) {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderRequest.getItems().forEach((itemId, count) -> {
            Item item = itemRepository.findById(itemId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
            orderItemList.add(OrderItem.createOrderItem(item, count));
        });
        return orderItemList;
    }

    private String generateOrderNumber(LocalDateTime dateTime) {
        String datePart = dateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyMMdd"));
        String uniquePart = UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        return "ORDER-" + datePart + uniquePart;
    }
}
