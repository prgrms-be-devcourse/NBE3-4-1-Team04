package com.example.gc_coffee.domain.order.orderItem.repository;

import com.example.gc_coffee.domain.order.orderItem.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    void deleteAllByOrderId(Long orderId);
}
