package com.spring.instafeed;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// 브랜치 다시 생성
@EnableJpaAuditing
@SpringBootApplication
public class InstafeedApplication {

    public static void main(String[] args) {
        SpringApplication.run(InstafeedApplication.class, args);
    }
}
