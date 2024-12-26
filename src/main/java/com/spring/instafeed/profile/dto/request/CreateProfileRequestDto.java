package com.spring.instafeed.profile.dto.request;

public record CreateProfileRequestDto (
        Long userId,
        String nickname,
        String imagePath,
        String content
) {}
