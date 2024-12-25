package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

public record UpdateNewsfeedResponseDto(
        Long id,
        String nickname,
        String content,
        String imagePath
) {

    public static UpdateNewsfeedResponseDto toDto(Newsfeed newsfeed) {
        return new UpdateNewsfeedResponseDto(
                newsfeed.getId(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getContent(),
                newsfeed.getImagePath()
        );
    }
}