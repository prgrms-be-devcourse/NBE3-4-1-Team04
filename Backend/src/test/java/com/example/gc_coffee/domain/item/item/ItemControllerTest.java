package com.example.gc_coffee.domain.item.item;

import com.example.gc_coffee.domain.item.ItemService.ItemService;
import com.example.gc_coffee.domain.item.controller.ItemController;
import com.example.gc_coffee.domain.item.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ItemControllerTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("유저, 상품 조회")
    void t1() throws Exception{
        ResultActions resultActions = mvc
                .perform(
                        get("/api/items?page=1&pageSize=4")
                )
                .andDo(print());

        List<Item> items = itemService.findAllByOrderByIdDesc();
        assertEquals(4, items.size());

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("items"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저, 상품 단건 조회")
    void t2() throws Exception{
        ResultActions resultActions = mvc
                .perform(
                        get("/api/items/1")
                )
                .andDo(print());

        Item item = itemService.findById(1).get();
        assertEquals("탄맛 커피콩",item.getItemName());

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.itemName").value("탄맛 커피콩"))
                .andExpect(jsonPath("$.itemPrice").value(500))
                .andExpect(jsonPath("$.quantity").value(10));

    }

    @Test
    @DisplayName("유저, 상품 상세 조회")
    void t3() throws Exception{
        ResultActions resultActions = mvc
                .perform(
                        get("/api/items/1/details")
                )
                .andDo(print());

        Item item = itemService.findById(1).get();
        assertEquals("탄맛 커피콩",item.getItemName());

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("itemDetails"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.category").value("COFFEE_BEAN"))
                .andExpect(jsonPath("$.itemName").value("탄맛 커피콩"))
                .andExpect(jsonPath("$.itemPrice").value(500))
                .andExpect(jsonPath("$.quantity").value(10));
                // todo: 상품 상세 설명 들어가 코드 필요함

    }
}
