package com.example.gc_coffee.domain.order.controller;

import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.order.order.controller.OrderController;
import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.Order;
import com.example.gc_coffee.domain.order.order.service.OrderService;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class OrderControllerTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("초기 데이터 기반 주문 생성 테스트")
    void createOrderWithSampleDataTest() throws Exception {
        ResultActions resultActions = mvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "user4@example.com",
                                    "address": "서울시 강남구",
                                    "items": [
                                        {"id": 1, "quantity": 1, "itemPrice": 500},
                                        {"id": 2, "quantity": 1, "itemPrice": 400}
                                    ]
                                }
                                """)
                        .contentType(
                                new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        ).andDo(print());

        Order order = orderService.fineLatest().get();

        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createOrder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("user4@example.com"))
                .andExpect(jsonPath("$.address").value("서울시 강남구"))
                .andExpect(jsonPath("$.orderPrice").value(900))
                .andExpect(jsonPath("$.orderStatus").value("ORDERED"));
    }

    @Test
    @DisplayName("전체 주문 조회 테스트")
    void getAllOrdersTest() throws Exception {
        // WHEN: 전체 주문 조회 요청
        ResultActions resultActions = mvc.perform(
                get("/api/orders/all")
        ).andDo(print());

        // THEN: 응답 검증
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3)); // 초기 데이터로 3개의 주문이 존재함
    }

    @Test
    @DisplayName("초기 데이터 기반 주문 ID로 조회 테스트")
    void getOrderByIdWithSampleDataTest() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/api/orders/id/1")
        ).andDo(print());

        OrderResponse order = orderService.getOrderById(1L);

        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("getOrdersById"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(order.getEmail()))
                .andExpect(jsonPath("$.address").value(order.getAddress()))
                .andExpect(jsonPath("$.orderPrice").value(order.getOrderPrice()))
                .andExpect(jsonPath("$.orderStatus").value(order.getOrderStatus().name()));
    }

    @Test
    @DisplayName("주문 번호로 조회 테스트")
    void getOrderByOrderNumberWithSampleDataTest() throws Exception {
        OrderResponse order = orderService.createOrder(OrderRequest.builder()
                .email("test@example.com")
                .address("테스트 주소")
                .items(List.of(
                        new ItemDto(1L, null, null, "item1", null, 5000, null, 1)
                ))
                .build());

        String orderNumber = order.getOrderNumber(); // 생성된 주문 번호 가져오기

        ResultActions resultActions = mvc.perform(
                get("/api/orders/number/" + orderNumber)
        ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(order.getEmail()))
                .andExpect(jsonPath("$.address").value(order.getAddress()))
                .andExpect(jsonPath("$.orderPrice").value(order.getOrderPrice()))
                .andExpect(jsonPath("$.orderStatus").value(order.getOrderStatus().name()));
    }



    @Test
    @DisplayName("초기 데이터 기반 모든 주문 조회 테스트")
    void getAllOrdersWithSampleDataTest() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/api/orders/all")
        ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3)); // 초기 데이터로 3개의 주문이 존재
    }

    @Test
    @DisplayName("초기 데이터 기반 주문 취소 테스트")
    void cancelOrderWithSampleDataTest() throws Exception {
        // GIVEN: 데이터 초기화 및 주문 생성
        OrderResponse order = orderService.createOrder(OrderRequest.builder()
                .email("test@example.com")
                .address("테스트 주소")
                .items(List.of(
                        new ItemDto(1L, null, null, "item1", null, 5000, null, 1)
                ))
                .build());

        String orderNumber = order.getOrderNumber(); // 생성된 주문 번호 가져오기

        ResultActions resultActions = mvc.perform(
                put("/api/orders/cancel/" + orderNumber)
        ).andDo(print());

        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("cancelOrder"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("CANCELED"));

        mvc.perform(get("/api/orders/number/" + orderNumber))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderStatus").value("CANCELED"));
    }

    @Test
    @DisplayName("초기 데이터 기반 주문 삭제 테스트")
    void deleteOrderWithSampleDataTest() throws Exception {
        ResultActions resultActions = mvc.perform(
                delete("/api/orders/1")
        ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("주문이 삭제되었습니다."));

        mvc.perform(get("/api/orders/1"))
                .andExpect(status().isNotFound());
    }
}
