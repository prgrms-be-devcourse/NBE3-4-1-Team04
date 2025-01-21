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
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;


    public OrderResponse createOrder(OrderRequest orderRequest) {
        // 동일 이메일로 ORDERED 상태의 주문이 있는지 확인
        Optional<Order> existingOrderOpt = findExistingOrder(orderRequest.getEmail());

        // 새로운 OrderItem 리스트 생성
        List<OrderItem> newOrderItems = createOrderItems(orderRequest);

        Order order = existingOrderOpt
                .map(existingOrder -> {
                    // 기존 주문에 아이템 추가
                    return addItemsToExistingOrder(existingOrder, newOrderItems);
                })
                .orElseGet(() -> {
                    // 새 주문 생성
                    return createNewOrder(orderRequest, newOrderItems);
                });

        saveOrderAndItems(order);

        return OrderResponse.of(order);
    }



    //동일한 이메일의 주문 완료 이전인 주문을 조회
    private Optional<Order> findExistingOrder(String email) {
        return orderRepository.findAllByEmail(email).stream()
                .filter(order -> order.getOrderStatus() == OrderStatus.ORDERED)
                .findFirst();
    }

    //이미 존재하는 주문에 아이템 추가
    private Order addItemsToExistingOrder(Order existingOrder, List<OrderItem> orderItemList) {
        orderItemList.forEach(existingOrder::addOrderItem);
        existingOrder.calculateOrderPrice();
        return existingOrder;
    }

    //새로운 주문
    private Order createNewOrder(OrderRequest orderRequest, List<OrderItem> orderItemList) {
        String orderNumber = generateOrderNumber(LocalDateTime.now()); //주문 번호

        Order order = Order.createOrder(
                orderRequest.getEmail(),
                orderRequest.getAddress(),
                orderNumber,
                orderItemList
        );

        order.calculateOrderPrice(); // 총 가격 계산
        return order;
    }

    public OrderResponse modify(Long orderId, String address, Map<Long, Integer> newItemList) {
        // 주문 조회
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ORDER));

        // 주소 변경
        if (address != null && !address.isEmpty()) {
            order.setAddress(address);
        }

        // 기존 아이템 제거 및 재고 복구 (명시적으로 제거 목록 처리)
        List<OrderItem> itemsToRemove = new ArrayList<>(order.getOrderItems());
        for (OrderItem orderItem : itemsToRemove) {
            orderItem.getItem().addStock(orderItem.getCount());
            orderItem.unlinkOrder();
        }
        order.getOrderItems().clear();

        // 새로운 아이템으로 초기화
        newItemList.forEach((itemId, count) -> {
            if (count > 0) {
                Item item = itemRepository.findById(itemId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_ITEM));
                item.removeStock(count);

                OrderItem newOrderItem = new OrderItem();
                newOrderItem.setItem(item);
                newOrderItem.setCount(count);
                order.addOrderItem(newOrderItem);
            }
        });

        // 주문 총 가격 다시 계산
        order.calculateOrderPrice();

        // 변경사항 저장
        orderRepository.save(order);

        // 응답 생성 및 반환
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
    public OrderResponse updateOrderStatus(long orderId, OrderStatus orderStatus) {
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

    public void flush() {
        orderRepository.flush();
    }
}
