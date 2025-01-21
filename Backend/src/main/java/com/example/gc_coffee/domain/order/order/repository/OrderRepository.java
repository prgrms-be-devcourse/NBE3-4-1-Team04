package com.example.gc_coffee.domain.order.order.repository;

import com.example.gc_coffee.domain.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findAllByEmail(String email);

    Optional<Order> findFirstByOrderByIdDesc();

    long countByEmail(String email);
}