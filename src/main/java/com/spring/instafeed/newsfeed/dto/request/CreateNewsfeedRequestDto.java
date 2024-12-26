package com.spring.instafeed.newsfeed.dto.request;

public record CreateNewsfeedRequestDto(
        Long profileId,
        String imagePath,
        String content
) {
}