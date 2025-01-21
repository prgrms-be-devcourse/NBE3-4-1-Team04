package com.example.gc_coffee.domain.admin.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin/login"; // 로그인 페이지 경로
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard"; // 대시보드 페이지 경로
    }
}
