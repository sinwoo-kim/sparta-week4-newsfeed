package com.spring.instafeed.profile.dto.request;

public record UpdateProfileRequestDto (
        String nickname,
        String content,
        String imagePath
) {}
