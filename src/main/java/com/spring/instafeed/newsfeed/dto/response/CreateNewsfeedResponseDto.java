package com.spring.instafeed.newsfeed.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.instafeed.newsfeed.entity.Newsfeed;

import java.time.LocalDateTime;

public record CreateNewsfeedResponseDto(
        Long id,
        String nickname,
        String imagePath,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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