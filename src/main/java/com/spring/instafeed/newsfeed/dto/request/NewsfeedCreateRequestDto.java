package com.spring.instafeed.newsfeed.dto.request;

import lombok.Getter;

@Getter
public class NewsfeedCreateRequestDto {

    private String imagePath;
    private String nickname;
    private String content;

}
