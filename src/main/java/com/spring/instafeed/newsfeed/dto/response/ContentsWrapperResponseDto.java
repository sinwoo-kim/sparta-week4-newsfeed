package com.spring.instafeed.newsfeed.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

public record ContentsWrapperResponseDto(List<ReadNewsfeedResponseDto> contents) {

}