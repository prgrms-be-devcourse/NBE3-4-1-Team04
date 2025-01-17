package com.example.gc_coffee.domain.order.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
//    private final ItemRepository itemRepository;
//    private final OrderRepository orderRepository;
//
//    // (조회) searchKeywordType 에 따라 상품 노출
//    public Page<Item> findByPaged(String searchKeywordType, String searchKeyword, int page, int pageSize) {
//        PageRequest pageRequest = PageRequest.of(page - 1, pageSize, Sort.by(Sort.Order.desc("id")));
//
//        if (Ut.str.isBlank(searchKeyword)) { return itemRepository.findAll(pageRequest);}
//
//        searchKeyword = "%" + searchKeyword + "%";
//
//        return switch (searchKeywordType) {
//            case "email" -> orderRepository.findByEmailLikeIgnoreCase(searchKeyword, pageRequest);
//            case "zipCode" -> orderRepository.findByZipCodeLikeIgnoreCase(searchKeyword, pageRequest);
//            default ->  itemRepository.findByItemNameLikeIgnoreCase(searchKeyword, pageRequest);
//        };
//
//    }
}
