package com.spring.instafeed.auth.dto.response;

public record AccessTokenResponseDto(String accessToken, String tokenType, Long userId, String message) {
}
