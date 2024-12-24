package com.spring.instafeed.user.dto.request;

public record SignUpUserRequestDto(
        String name,
        String email,
        String password
) {
}