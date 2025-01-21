package com.example.gc_coffee.domain.admin.controller;

import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.item.entity.Category;
import com.example.gc_coffee.domain.item.entity.Item;
import com.example.gc_coffee.domain.item.service.ItemService;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
@AutoConfigureMockMvc
@WithMockUser(username = "admin", roles = {"ADMIN"}) //Admin 테스트
public class AdminControllerTest {

    @Autowired
    private ItemService itemService;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("상품 등록 API 테스트")
    void addItemTest() throws Exception {
        // given: 테스트에 필요한 데이터 준비
        ItemDto requestDto = new ItemDto();
        requestDto.setItemName("아메리카노");
        requestDto.setCategory(Category.COFFEE);
        requestDto.setItemPrice(4500);
        requestDto.setItemImage("/image.png");
        requestDto.setQuantity(100);
        requestDto.setItemDescription("신선한 원두로 만든 커피");

        // when: API 호출 및 테스트 수행
        mvc.perform(post("/api/admin/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                // then: 응답 상태 및 결과 검증
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("아메리카노"))
                .andExpect(jsonPath("$.category").value("COFFEE"))
                .andExpect(jsonPath("$.itemPrice").value(4500))
                .andExpect(jsonPath("$.itemImage").value("/image.png"))
                .andExpect(jsonPath("$.quantity").value(100))
                .andExpect(jsonPath("$.itemDescription").value("신선한 원두로 만든 커피"));
    }

    @Test
    @DisplayName("상품 삭제")
    void delete() throws Exception {
        Item item = itemService.findById(4).get();
        assertEquals("탄맛 쥬스", item.getItemName());

        ResultActions resultActions = mvc
                .perform(
                        patch("/api/admin/delete/4")
                )
                .andDo(print());

        resultActions
                .andExpect(handler().handlerType(AdminController.class))
                .andExpect(handler().methodName("deleteItem"))
                .andExpect(status().isOk());

        assertThat(item.isDeleted()).isTrue();

        List<Item> itemList = itemService.findAllByOrderByIdDesc();
        assertThat(itemList.size()).isEqualTo(3); //삭제된것 제외시키고 출력
    }

    @Test
    @DisplayName("전체 주문 조회 테스트")
    void getAllOrdersTest() throws Exception {
        // WHEN: 전체 주문 조회 요청
        ResultActions resultActions = mvc.perform(
                get("/api/admin/all")
        ).andDo(print());

        // THEN: 응답 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2)); // 초기 데이터로 3개의 주문이 존재함
    }

    @Test
    @DisplayName("주문 상태 수정 테스트")
    void updateOrderStatusTest() throws Exception {
        // GIVEN: 상태를 수정할 주문 ID와 새로운 상태
        Long orderId = 2L; // 상태를 수정할 주문 ID
        OrderStatus newStatus = OrderStatus.COMPLETED; // 변경할 상태

        // WHEN: 상태 수정 요청
        ResultActions resultActions = mvc.perform(
                patch("/api/admin/status/" + orderId)
                        .param("order_status", newStatus.name()) // 요청 파라미터 추가
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print());

        // THEN: 응답 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value(newStatus.name())); // 상태가 올바르게 변경되었는지 검증
    }



}

