package com.example.gc_coffee.domain.admin.login;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AdminLoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("관리자 로그인 성공 테스트")
    void testAdminLoginSuccess() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "team4@admin.com")
                        .param("password", "admin_team4")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isFound()) // 로그인 성공 시 302 리다이렉트
                .andExpect(redirectedUrl("/swagger-ui/index.html")); // 성공 후 리다이렉션 URL 확인
    }

    @Test
    @DisplayName("관리자 로그인 실패 테스트 - 잘못된 비밀번호")
    void testAdminLoginFailIncorrectPassword() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "team4@admin.com")
                        .param("password", "wrong_password")
                        .with(SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isFound()) // 실패 시 리다이렉션
                .andExpect(redirectedUrl("/admin/login?error=true")); // 실패 후 리다이렉션 URL 확인
    }

    @Test
    @DisplayName("관리자 로그인 실패 테스트 - 잘못된 이메일")
    void testAdminLoginFailIncorrectEmail() throws Exception {
        mockMvc.perform(post("/admin/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("username", "wrong@admin.com")
                        .param("password", "admin_team4")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())) // CSRF 토큰 추가
                .andExpect(status().isFound()) // 실패 시 리다이렉션
                .andExpect(redirectedUrl("/admin/login?error=true")); // 실패 후 리다이렉션 URL 확인
    }
}
