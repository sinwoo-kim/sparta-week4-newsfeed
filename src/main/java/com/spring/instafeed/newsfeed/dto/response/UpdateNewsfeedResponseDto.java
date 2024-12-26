package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

import java.time.LocalDateTime;

public record UpdateNewsfeedResponseDto(
        Long id,
        String nickname,
        String imagePath,
        String content,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static UpdateNewsfeedResponseDto toDto(Newsfeed newsfeed) {
        return new UpdateNewsfeedResponseDto(
                newsfeed.getId(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getImagePath(),
                newsfeed.getContent(),
                newsfeed.getCreatedAt(),
                newsfeed.getUpdatedAt()
        );
    }
}