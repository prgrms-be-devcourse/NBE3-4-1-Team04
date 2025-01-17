package com.example.gc_coffee.domain.order.order.service;

import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.repository.OrderRepository;
import com.example.gc_coffee.global.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
//    private final OrderRepository orderRepository;
//
//    // (조회) %상품, 이메일, 주문번호% 조회
//    public Page<Order> findByPaged(String searchKeywordType, String searchKeyword, int page, int pageSize) {
//        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id")));
//
//        if (Ut.str.isBlank(searchKeyword)) { return orderRepository.findAll(pageRequest); }
//
//        searchKeyword = "%" + searchKeyword + "%";
//
//        // Todo
//        return switch (searchKeywordType) {
//            case "email" -> orderRepository.findByEmailLikeIgnoreCase(searchKeyword, pageRequest);
//            case "orderNumber" -> orderRepository.findByZipCodeLikeIgnoreCase(searchKeyword, pageRequest);
//            default -> orderRepository.findByOrderItemsNameLikeIgnoreCase(searchKeyword, pageRequest);
//        };
//    }
}
