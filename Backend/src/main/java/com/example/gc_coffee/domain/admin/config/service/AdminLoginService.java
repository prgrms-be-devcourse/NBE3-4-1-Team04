package com.example.gc_coffee.domain.admin.config.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginService implements UserDetailsService {

    private final String ADMIN_EMAIL = "team4@admin.com"; // 관리자 이메일
    private final String ADMIN_PASSWORD = "$2a$10$E9ZnZt6ENHw7N4V10Uoc7e4zRt4Jsy69xI8IW41jFIuA2hLYtH.ny"; // 암호화된 비밀번호

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!username.equals(ADMIN_EMAIL)) {
            throw new UsernameNotFoundException("관리자 이메일 또는 비밀번호가 올바르지 않습니다.");
        }

        return User.builder()
                .username(ADMIN_EMAIL)
                .password(ADMIN_PASSWORD)
                .roles("ADMIN")
                .build();
    }
}
