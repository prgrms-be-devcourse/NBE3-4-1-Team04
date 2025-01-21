package com.example.gc_coffee.domain.order.order.repository;

import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findAllByEmail(String email);

    Optional<Order> findFirstByOrderByIdDesc();

    long countByEmail(String email);


    //배치 처리
    @Query(value = "SELECT DISTINCT o FROM Order o " +
            "JOIN FETCH o.orderItems oi " +
            "JOIN FETCH oi.item " +
            "WHERE o.orderDate BETWEEN :startTime AND :endTime " +
            "AND o.orderStatus = :status",
            countQuery = "SELECT COUNT(o) FROM Order o " +
                    "WHERE o.orderDate BETWEEN :startTime AND :endTime " +
                    "AND o.orderStatus = :status")
    Page<Order> findByOrderTimeBetweenAndOrderStatus(
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            @Param("status") OrderStatus status,
            Pageable pageable
    );

}