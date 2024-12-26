package com.spring.instafeed.auth.dto.request;

public record SignUpRequestDto(
        String email,
        String password,
        String name
) {
}