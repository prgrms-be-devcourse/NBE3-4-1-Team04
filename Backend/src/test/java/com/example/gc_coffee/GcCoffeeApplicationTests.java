package com.example.gc_coffee;

import com.example.gc_coffee.domain.item.ItemService.ItemService;
import com.example.gc_coffee.domain.item.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
class GcCoffeeApplicationTests {
	@Autowired
	private ItemService itemService;

	@Autowired
	private MockMvc mvc;

	@Test
	@DisplayName("유저 상품 조회")
	void t1() throws Exception{
		//given
		List<Item> item = itemService.findByAll();
		assertEquals(2, item.size());



		//then
	}

}
