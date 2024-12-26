package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

public record ReadNewsfeedResponseDto(
        Long id,
        String nickname,
        String imagePath,
        String content
) {

    public static ReadNewsfeedResponseDto toDto(
            Newsfeed newsfeed
    ) {
        return new ReadNewsfeedResponseDto(
                newsfeed.getId(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getImagePath(),
                newsfeed.getContent()
        );
    }
}