package com.example.gc_coffee.domain.admin.controller;

import com.example.gc_coffee.domain.item.dto.ItemDto;
import com.example.gc_coffee.domain.item.entity.Category;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

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
        mockMvc.perform(post("/api/admin/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                // then: 응답 상태 및 결과 검증
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.itemName").value("아메리카노"))
                .andExpect(jsonPath("$.category").value("COFFEE"))
                .andExpect(jsonPath("$.itemPrice").value(4500))
                .andExpect(jsonPath("$.itemImage").value("http://example.com/image.png"))
                .andExpect(jsonPath("$.quantity").value(100))
                .andExpect(jsonPath("$.itemDescription").value("신선한 원두로 만든 커피"));
    }
}
