package com.example.gc_coffee.domain.item.item;

import com.example.gc_coffee.domain.item.controller.ItemController;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = {"spring.test.database.replace=ANY"})
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
    void t1() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/api/items?page=1&pageSize=4")
                )
                .andDo(print());             // JSON 응답 데이터를 콘솔에 출력


        List<Item> items = itemService.findAllByOrderByIdDesc();
        assertEquals(4, items.size());

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("items"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("유저, 상품명 단건 조회")
    void t2() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        get("/api/items/1")
                )
                .andDo(print());


        Item item = itemService.findById(1).get();

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("item"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(item.getId()))
                .andExpect(jsonPath("$.category").value(item.getCategory().name())) // Enum name()으로 비교
                .andExpect(jsonPath("$.itemName").value(item.getItemName()))
                .andExpect(jsonPath("$.itemPrice").value(item.getItemPrice()))
                .andExpect(jsonPath("$.quantity").value(item.getQuantity()))
                .andExpect(jsonPath("$.itemDescription").value(item.getItemDescription()));
    }


    @Test
    @DisplayName("상품 등록")
    void t3() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        post("/api/items")
                                .content("""
                                        {
                                            "category":"TEA",
                                            "itemName":"과자에 차 한잔?",
                                            "itemPrice":1000,
                                            "itemImage":"사진이 아직 없어요",
                                            "itemQuantity":10,
                                            "itemDescription":"한정판매 합니다."
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
                )
                .andDo(print());

        Item item = itemService.fineLatest().get();
        assertThat(item.getItemName()).isEqualTo("과자에 차 한잔?");

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("addItem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(5))
                .andExpect(jsonPath("$.category").value("TEA"))
                .andExpect(jsonPath("$.itemName").value("과자에 차 한잔?"))
                .andExpect(jsonPath("$.itemPrice").value(1000))
                .andExpect(jsonPath("$.itemImage").value("사진이 아직 없어요"))
                .andExpect(jsonPath("$.quantity").value(10))
                .andExpect(jsonPath("$.itemDescription").value("한정판매 합니다."));
    }

    @Test
    @DisplayName("상품 수정")
    void t4() throws Exception {
        ResultActions resultActions = mvc
                .perform(
                        patch("/api/items/1")
                                .content("""
                                        {
                                            "category":"COFFEE",
                                            "itemName":"아침엔 모닝커피?",
                                            "itemPrice":500,
                                            "itemImage":"모닝 커피를 먹는 사람의 사진",
                                            "itemQuantity":100,
                                            "itemDescription":"어서오세요~"
                                        }
                                        """)
                                .contentType(
                                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                                )
                )
                .andDo(print());

        Item item = itemService.findById(1).get();
        assertEquals("아침엔 모닝커피?", item.getItemName());

        resultActions
                .andExpect(handler().handlerType(ItemController.class))
                .andExpect(handler().methodName("modifyItem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.category").value("COFFEE"))
                .andExpect(jsonPath("$.itemName").value("아침엔 모닝커피?"))
                .andExpect(jsonPath("$.itemPrice").value(500))
                .andExpect(jsonPath("$.itemImage").value("모닝 커피를 먹는 사람의 사진"))
                .andExpect(jsonPath("$.quantity").value(100))
                .andExpect(jsonPath("$.itemDescription").value("어서오세요~"));
    }

}
