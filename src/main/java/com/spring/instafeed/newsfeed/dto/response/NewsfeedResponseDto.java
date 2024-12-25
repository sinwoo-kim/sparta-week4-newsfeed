package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

public record NewsfeedResponseDto(
        String imagePath,
        String nickname,
        String content
) {

    public static NewsfeedResponseDto toDto(Newsfeed newsfeed) {
        return new NewsfeedResponseDto(
                newsfeed.getImagePath(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getContent()
        );
    }
}