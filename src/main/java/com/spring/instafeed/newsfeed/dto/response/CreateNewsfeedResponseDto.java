package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

public record CreateNewsfeedResponseDto(
        Long id,
        String nickname,
        String content,
        String imagePath
) {

    public static CreateNewsfeedResponseDto toDto(Newsfeed newsfeed) {
        return new CreateNewsfeedResponseDto(
                newsfeed.getId(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getContent(),
                newsfeed.getImagePath()
        );
    }
}