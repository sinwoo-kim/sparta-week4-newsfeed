package com.spring.instafeed.auth.dto.response;

public record AccessTokenResponseDto(String accessToken, String tokenType, int expiresIn, String scope) {
}
