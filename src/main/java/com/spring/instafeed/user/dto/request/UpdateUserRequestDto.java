package com.spring.instafeed.user.dto.request;

public record UpdateUserRequestDto(
        String oldPassword,
        String newPassword
) {
}