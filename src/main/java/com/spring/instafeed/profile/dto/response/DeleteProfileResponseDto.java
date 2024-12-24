package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;

import java.time.LocalDateTime;

public record DeleteProfileResponseDto(
    Long id,
    Long userId,
    String nickname,
    String content,
    String imagePath,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    Boolean isDeleted,
    LocalDateTime deletedAt
) {}
