package com.example.gc_coffee.domain.admin.login;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2-console/**")
                        .permitAll()
                        .requestMatchers("/admin/**", "/api/admin/**").hasRole("ADMIN") // "/admin/**" 경로는 ADMIN만 접근 가능
                        .anyRequest().permitAll() // 나머지 경로는 인증 없이 접근 가능
                )
                .formLogin(form -> form
                        .loginPage("/admin/login") // 사용자 정의 로그인 페이지 경로
                        .loginProcessingUrl("/admin/login") // 로그인 처리 URL
                        .defaultSuccessUrl("/admin/dashboard", true) // 로그인 성공 후 이동 경로
                        .failureUrl("/admin/login?error=true") // 로그인 실패 시 이동 경로
                        .permitAll() // 로그인 페이지는 누구나 접근 가능
                )
                .logout(logout -> logout
                        .logoutUrl("/admin/logout") // 로그아웃 처리 URL
                        .logoutSuccessUrl("/") // 로그아웃 성공 후 이동 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll()
                )
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 인증되지 않은 사용자 접근 시 401 에러 반환
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.getWriter().write("Unauthorized: " + authException.getMessage());
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // 권한 부족 시 403 에러 반환
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.getWriter().write("Forbidden: " + accessDeniedException.getMessage());
                        })
                )
                .headers(
                        headers ->
                                headers.frameOptions(
                                        frameOptions ->
                                                frameOptions.sameOrigin() //h2콘솔
                                )
                )
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화 (필요에 따라 활성화 가능)
        ;

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // 관리자 계정 설정 (메모리 기반)
        UserDetails admin = User.builder()
                .username("team4@admin.com")
                .password(passwordEncoder().encode("admin_team4")) // 비밀번호 암호화
                .roles("ADMIN") // ADMIN 역할 부여
                .build();

        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService()); // UserDetailsService 연결
        provider.setPasswordEncoder(passwordEncoder()); // PasswordEncoder 연결
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager(); // AuthenticationManager 설정
    }
}
