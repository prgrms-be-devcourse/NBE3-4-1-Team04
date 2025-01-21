package com.example.gc_coffee.domain.order.order.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @Schema(description = "사용자 이메일", example = "vkdnjdjxor@naver.com")
    private String email;

    @Schema(description = "배송 주소", example = "서울시 강남구")
    private String address;

    @Schema(description = "주문 항목 목록 (상품 ID와 수량)", example = "{\"1\": 1}")
    private Map<Long, Integer> items;
}