package com.spring.instafeed.profile.dto.response;

import java.time.LocalDateTime;

public record UpdateProfileResponseDto(
    Long id,
    Long userId,
    String nickname,
    String content,
    String imagePath,
    LocalDateTime createdAt,
    LocalDateTime updatedAt
) {}