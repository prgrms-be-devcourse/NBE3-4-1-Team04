package com.example.gc_coffee.domain.order.order.repository;

import com.example.gc_coffee.domain.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
