package com.example.gc_coffee.domain.admin.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminLoginController {

    @GetMapping("/admin/login")
    public String loginPage() {
        return "admin/login"; // 로그인 페이지 경로 login.html 템플릿 반환
    }
    //Todo dashboard 페이지 프론트에서 구현
//    @GetMapping("/admin/dashboard")
//    public String dashboard() {
//        return "admin/dashboard"; // 대시보드 페이지 경로
//    }
}
