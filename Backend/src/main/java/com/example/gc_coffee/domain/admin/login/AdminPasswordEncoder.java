package com.example.gc_coffee.domain.admin.login;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AdminPasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "admin_team4"; // 암호화할 원본 비밀번호
        String encodedPassword = encoder.encode(rawPassword);

        System.out.println("암호화된 비밀번호: " + encodedPassword);


    }
}