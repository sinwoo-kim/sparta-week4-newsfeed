package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

public record NewsfeedCommonResponseDto(String imagePath, String nickname, String content) {

    public static NewsfeedCommonResponseDto convertToDto(Newsfeed newsfeed) {
        return new NewsfeedCommonResponseDto(
                newsfeed.getImagePath(),
                newsfeed.getProfile().getNickname(),
                newsfeed.getContent()
        );
    }
}
