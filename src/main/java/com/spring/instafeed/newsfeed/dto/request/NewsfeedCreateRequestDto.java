package com.spring.instafeed.newsfeed.dto.request;

public record NewsfeedCreateRequestDto(String imagePath, String content, Long profileId) {
}
