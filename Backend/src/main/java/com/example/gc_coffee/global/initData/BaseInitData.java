package com.example.gc_coffee.global.initData;

import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final ItemService itemService;
    private final OrderService orderService;
    @Autowired
    @Lazy
    private BaseInitData self;

    @Bean
    public ApplicationRunner BaseInitDataApplicationRunner() {
        return args -> {
            self.itemSampleData();
            self.cartSampleData();
            self.orderTestSampleData();
        };
    }

    @Transactional
    public void itemSampleData() {
        if (itemService.count() > 0) return;
        if (orderService.count() > 0) return;
        Item item1 = itemService.addItem(
                Category.COFFEE_BEAN,
                "탄맛 커피콩", 500,
                "탄맛 커피콩 사진", 10,
                "탓네...");

        Item item2 = itemService.addItem(
                Category.COFFEE,
                "탄맛 커피", 400,
                "탄맛 커피 사진", 10,
                "또 탓어?");

        Item item3 = itemService.addItem(
                Category.TEA,
                "탄맛 티", 300,
                "탄맛 티 사진", 10,
                "차를 어떻게 태운거야?");

        Item item4 = itemService.addItem(
                Category.JUICE,
                "탄맛 쥬스", 200,
                "탄맛 쥬스 사진", 10,
                "하다하다 쥬스도 ㅋㅋ");


        OrderRequest order1 = OrderRequest.builder()
                .email("user2@example.com")
                .address("서울시 서초구")
                .items(
                        List.of(
                                new ItemDto(item1),
                                new ItemDto(item2)
                                )
                        )
                        .build();
        orderService.createOrder(order1);

        OrderRequest order2 = OrderRequest.builder()
                .email("user2@example.com")
                .address("서울시 서초구")
                .items(
                        List.of(
                                new ItemDto(item3),
                                new ItemDto(item4)
                        )
                )
                .build();
        orderService.createOrder(order2);

        OrderRequest order3 = OrderRequest.builder()
                .email("user2@example.com")
                .address("서울시 서초구")
                .items(
                        List.of(
                                new ItemDto(item1),
                                new ItemDto(item2),
                                new ItemDto(item3),
                                new ItemDto(item4)
                        )
                )
                .build();
        orderService.createOrder(order3);

    }

    @Transactional
    public void cartSampleData() {
        // Todo: 장바구니 추가 API 구현 후 테스트_작업자:OS
    }

    @Transactional
    public void orderTestSampleData() {
        // Todo: 주문 API 완료 후 진행_작업자:HS
    }
}
