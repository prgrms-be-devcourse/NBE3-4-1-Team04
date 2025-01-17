package com.example.gc_coffee.domain.cart.controller;

import com.example.gc_coffee.domain.cart.service.CartService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
@Tag(name = "장바구니", description = "장바구니 API")
public class CartController {
    private final CartService cartService;
}
