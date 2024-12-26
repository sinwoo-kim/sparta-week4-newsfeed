package com.spring.instafeed.auth.dto.request;

public record LoginRequestDto(
        String email,
        String password
) {
}