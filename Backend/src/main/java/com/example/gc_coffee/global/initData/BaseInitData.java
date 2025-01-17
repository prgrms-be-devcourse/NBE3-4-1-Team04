package com.example.gc_coffee.global.initData;

import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.item.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@RequiredArgsConstructor
public class BaseInitData {
    private final ItemService itemService;

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
        if (itemService.count() > 0) return ;

        itemService.addItem(Category.COFFEE_BEAN, "탄맛 커피콩", 500, "탄맛 커피콩 사진", 10);
        itemService.addItem(Category.COFFEE, "탄맛 커피", 400, "탄맛 커피 사진", 10);
        itemService.addItem(Category.TEA, "탄맛 티", 300, "탄맛 티 사진", 10);
        itemService.addItem(Category.JUICE, "탄맛 쥬스", 200, "탄맛 쥬스 사진", 10);

    }

    @Transactional
    public void cartSampleData() {
        // todo: 장바구니 추가 API 구현 후 테스트_작업자:OS
    }

    @Transactional
    public void orderTestSampleData() {
        // todo: 주문 API 완료 후 진행_작업자:OS
    }
}
