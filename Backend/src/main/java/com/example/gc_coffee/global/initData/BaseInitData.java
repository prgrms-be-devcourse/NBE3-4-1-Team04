package com.example.gc_coffee.global.initData;

import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
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

        itemService.modify(
                item1, Category.COFFEE_BEAN,
                "짠맛 커피콩", 800,
                "너무 짜서 눈을 감는 사진", 100,
                "사실 소금아닐까?");

        itemService.modify(
                item2, Category.COFFEE,
                "고구마맛 커피", 1000,
                "고구마와 커피 사진", 100,
                "사실 고구마 맛이라네");

        itemService.modify(item3, Category.TEA,
                "곧 안팔려서 내릴지도 모르는 티", 500,
                "맛없어 하는 사진", 100,
                "어우 먹지마");

        itemService.delete(item4);
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
