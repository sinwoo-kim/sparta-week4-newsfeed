package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class NewsfeedCommonResponseDto {

    private String imagePath;
    private String nickname;
    private String content;

    private NewsfeedCommonResponseDto(String imagePath, String nickname, String content) {
        this.imagePath = imagePath;
        this.nickname = nickname;
        this.content = content;
    }

    public static NewsfeedCommonResponseDto convertDto(Newsfeed newsfeed) {
        return new NewsfeedCommonResponseDto(newsfeed.getImagePath(),newsfeed.getNickname(),newsfeed.getContent());
    }
}