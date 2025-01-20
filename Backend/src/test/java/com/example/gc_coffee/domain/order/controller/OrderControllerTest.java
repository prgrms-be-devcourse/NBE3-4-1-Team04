package com.example.gc_coffee.domain.order.controller;

import com.example.gc_coffee.domain.order.order.controller.OrderController;
import com.example.gc_coffee.domain.order.order.dto.OrderRequest;
import com.example.gc_coffee.domain.order.order.dto.OrderResponse;
import com.example.gc_coffee.domain.order.order.entity.OrderStatus;
import com.example.gc_coffee.domain.order.order.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(
    controllers = OrderController.class,
    excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
            JpaMetamodelMappingContext.class
        })
    }
)
@MockBean(JpaMetamodelMappingContext.class)
class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService orderService;

    @Test
    @DisplayName("새로운 주문을 생성할 수 있다")
    void createOrder() throws Exception {
        // given
        OrderRequest requestDto = createOrderRequestDto();
        OrderResponse responseDto = createOrderResponseDto();
        given(orderService.createOrder(any(OrderRequest.class)))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(requestDto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(responseDto.getOrderNumber()));
    }

    @Test
    @DisplayName("이메일로 주문 목록을 조회할 수 있다")
    void getOrdersByEmail() throws Exception {
        // given
        String email = "test@example.com";
        OrderResponse responseDtos = createOrderResponseDto();
        given(orderService.findByEmail(email))
                .willReturn(responseDtos);

        // when & then
        mockMvc.perform(get("/api/orders/email/{email}", email))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].orderNumber").value(responseDtos.get(0).getOrderNumber()));
    }

    @Test
    @DisplayName("주문 번호로 주문을 조회할 수 있다")
    void getOrderByNumber() throws Exception {
        // given
        String orderNumber = "ORDER-001";
        OrderResponse responseDto = createOrderResponseDto();
        given(orderService.findByOrderNumber(orderNumber))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(get("/api/orders/{orderNumber}", orderNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(orderNumber));
    }

    @Test
    @DisplayName("주문 상태를 수정할 수 있다")
    void updateOrderStatus() throws Exception {
        // given
        String orderNumber = "ORDER-001";
        OrderStatus newStatus = OrderStatus.COMPLETED;
        OrderResponse responseDto = createOrderResponseDto();
        given(orderService.updateOrderStatus(orderNumber, newStatus))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/orders/{orderNumber}/status", orderNumber)
                .param("status", newStatus.name()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(orderNumber));
    }

    @Test
    @DisplayName("주문을 취소할 수 있다")
    void cancelOrder() throws Exception {
        // given
        String orderNumber = "ORDER-001";
        OrderResponse responseDto = createOrderResponseDto();
        given(orderService.cancelOrder(orderNumber))
                .willReturn(responseDto);

        // when & then
        mockMvc.perform(put("/api/orders/{orderNumber}/cancel", orderNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderNumber").value(orderNumber));
    }

    private OrderRequest createOrderRequestDto() {
        return OrderRequest.builder()
                .email("Grids&Circles@example.com")
                .address("경상남도 거제시 00동")
                .items(Arrays.asList(
                        OrderRequest.builder()
                                .itemId(1L)
                                .quantity(2)
                                .price(10000)
                                .build()
                ))
                .build();
    }

    private OrderResponse createOrderResponseDto() {
        return OrderResponse.builder()
                .id(1L)
                .orderNumber("ORDER-001")
                .email("Grids&Circles@example.com")
                .address("경상남도 거제시 00동")
                .orderPrice(20000)
                .status(OrderStatus.ORDERED)
                .build();
    }
} 