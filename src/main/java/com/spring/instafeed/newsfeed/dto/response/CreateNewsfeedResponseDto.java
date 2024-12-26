package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

import java.time.LocalDateTime;

public record CreateNewsfeedResponseDto(
        Long id,
        String nickname,
        String imagePath,
        String content,
        LocalDateTime createdAt
) {

    public static CreateNewsfeedResponseDto toDto(Newsfeed newsfeed) {
        return new CreateNewsfeedResponseDto(
                newsfeed.getId(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getImagePath(),
                newsfeed.getContent(),
                newsfeed.getCreatedAt()
        );
    }
}