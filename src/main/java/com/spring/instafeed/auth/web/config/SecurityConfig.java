package com.spring.instafeed.auth.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 에서 모든 요청을 받도록 설정
 * 자동으로 만들어주는 login form 비활성화 설정
 */
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 : 악의적인 사이트에서 사용자의 인증된 세션을 악용해 요청을 보내는 공격
                .authorizeHttpRequests(auth -> // HTTP 요청에 대한 인증 및 권한을 설정
                        auth.anyRequest().permitAll() // 모든 요청을 인증 없이 허용
                )
                .formLogin(AbstractHttpConfigurer::disable); // 시큐리티가 기본적으로 제공하는 로그인 페이지 비활성화
        return http.build(); // HttpSecurity 객체를 빌드하여 SecurityFilterChain 객체를 생성
    }
}
