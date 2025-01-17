package com.example.gc_coffee.domain.order.order.repository;

import com.example.gc_coffee.domain.order.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

//    Page<Order> findByEmailLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);
//
//    Page<Order> findByZipCodeLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);
//
//    Page<Order> findByOrderItemsNameLikeIgnoreCase(String searchKeyword, PageRequest pageRequest);
}
