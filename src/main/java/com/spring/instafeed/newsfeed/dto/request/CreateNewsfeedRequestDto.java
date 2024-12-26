package com.spring.instafeed.newsfeed.dto.request;

public record CreateNewsfeedRequestDto(
        Long profileId,
        String content,
        String imagePath
) {
}