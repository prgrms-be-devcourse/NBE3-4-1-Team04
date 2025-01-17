package com.example.gc_coffee.domain.order.order.repository;

import com.example.gc_coffee.domain.order.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
//    Page<Item> findByEmailLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);
//
//    Page<Item> findByZipCodeLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);
}
