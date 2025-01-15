package com.example.gc_coffee.domain.item.item;

import com.example.gc_coffee.domain.item.ItemService.ItemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ApiV1ItemControllerTest {
    @Autowired
    private ItemService itemService;

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("유저 상품 조회")
    void test() throws Exception{

        //given

        //when

        //then
    }

}
