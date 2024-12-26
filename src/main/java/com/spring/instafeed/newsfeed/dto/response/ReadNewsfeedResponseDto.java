package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

public record ReadNewsfeedResponseDto(
        Long id,
        String nickname,
        String content,
        String imagePath
) {

    public static ReadNewsfeedResponseDto toDto(Newsfeed newsfeed) {
        return new ReadNewsfeedResponseDto(
                newsfeed.getId(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getContent(),
                newsfeed.getImagePath()
        );
    }
}