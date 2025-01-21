package com.example.gc_coffee.domain.order.controller;

import com.example.gc_coffee.domain.order.order.controller.OrderController;
import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
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
import java.util.Map;

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
    void t1() throws Exception {
        ResultActions resultActions = mvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "email": "user4@example.com",
                                    "address": "서울시 강남구",
                                    "items": {
                                         "1": 1,
                                         "2": 1
                                    }
                                }
                                """)
                        .contentType(
                                new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8))
        ).andDo(print());

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
    @DisplayName("초기 데이터 기반 주문 ID로 조회 테스트")
    void t2() throws Exception {
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
    void t3() throws Exception {
        OrderResponse order = orderService.createOrder(OrderRequest.builder()
                .email("test@example.com")
                .address("테스트 주소")
                .items(Map.of(1L, 2, 2L, 3))
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
    @DisplayName("초기 데이터 기반 주문 취소 테스트")
    void t4() throws Exception {
        OrderResponse order = orderService.createOrder(OrderRequest.builder()
                .email("test@example.com")
                .address("테스트 주소")
                .items(Map.of(1L, 2, 2L, 3))
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
    @DisplayName("전체 주문 조회 테스트")
    void t5() throws Exception {
        ResultActions resultActions = mvc.perform(
                get("/api/orders/count/user3@example.com")
        ).andDo(print());

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().string("1")); // 주문 통합
    }

    @Test
    @DisplayName("품절 처리")
    void t6_soldOut() throws Exception {
        // Given
        String content = """
            {
                "email": "user4@example.com",
                "address": "서울시 강남구",
                "items": {
                    "1": 100
                }
            }
            """;

        // When
        ResultActions resultActions = mvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .characterEncoding(StandardCharsets.UTF_8)
        ).andDo(print());

        // Then
        resultActions
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createOrder"))
                .andExpect(status().isBadRequest()) // 상태 코드가 400인지 확인
                .andExpect(jsonPath("$.statusCode").value("BAD_REQUEST")) // 상태 코드 문자열 확인
                .andExpect(jsonPath("$.body.status").value(400)) // HTTP 상태 코드 확인
                .andExpect(jsonPath("$.body.detail").value("재고가 부족합니다.")) // 에러 메시지 확인
                .andExpect(jsonPath("$.typeMessageCode").value("problemDetail.type.com.example.gc_coffee.global.exceptions.BusinessException")) // 예외 유형 코드 확인
                .andExpect(jsonPath("$.titleMessageCode").value("problemDetail.title.com.example.gc_coffee.global.exceptions.BusinessException")); // 제목 코드 확인
    }

    @Test
    @DisplayName("초기 데이터 기반 주문 병합 테스트")
    void t7_existOrderProcess() throws Exception {
        // Given
        String additionalOrderJson = """
        {
            "email": "user3@example.com",
            "address": "서울시 서초구",
            "items": {
                "1": 3,
                "4": 1
            }
        }
        """;

        // When
        ResultActions resultActions = mvc.perform(
                post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(additionalOrderJson)
                        .characterEncoding(StandardCharsets.UTF_8)
        ).andDo(print());

        // Calculate expected total price
        int expectedOrderPrice =
                (4 + 3) * 500 + // 탄맛 커피콩 (기존 4 + 추가 3)
                4 * 400 +      // 탄맛 커피
                4 * 300 +      // 탄맛 티
                (4 + 1) * 200; // 탄맛 쥬스 (기존 4 + 추가 1)

        // Then
        resultActions
                .andExpect(status().isOk())
                .andExpect(handler().handlerType(OrderController.class))
                .andExpect(handler().methodName("createOrder"))
                .andExpect(jsonPath("$.orderPrice").value(expectedOrderPrice));
    }


}
