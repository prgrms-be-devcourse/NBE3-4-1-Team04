package com.example.gc_coffee.global.initData;

import com.example.gc_coffee.domain.item.ItemService.ItemService;
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
            self.sampleData1();
            self.sampleData2();
        };
    }

    @Transactional
    public void sampleData1() {
        if (itemService.count() > 0) return ;

        itemService.addItem(Category.COFFEE_BEAN, "탄맛 커피콩", 500, "탄맛 커피콩 사진", 10);
        itemService.addItem(Category.COFFEE, "탄맛 커피", 400, "탄맛 커피 사진", 10);
        itemService.addItem(Category.TEA, "탄맛 티", 300, "탄맛 티 사진", 10);
        itemService.addItem(Category.JUICE, "탄맛 쥬스", 200, "탄맛 쥬스 사진", 10);

    }

    @Transactional
    public void sampleData2() {

    }
}
