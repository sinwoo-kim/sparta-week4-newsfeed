package com.spring.instafeed.newsfeed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ContentsWrapperResponseDto {
    private final List<ReadNewsfeedResponseDto> contents; // "contents"라는 키를 가지는 필드
}