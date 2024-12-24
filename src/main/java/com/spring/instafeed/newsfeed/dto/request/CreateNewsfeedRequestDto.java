package com.spring.instafeed.newsfeed.dto.request;

public record CreateNewsfeedRequestDto(String imagePath, String content, Long profileId) {
}
