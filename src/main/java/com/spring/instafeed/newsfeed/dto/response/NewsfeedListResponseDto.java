package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

import java.util.List;


public record NewsfeedListResponseDto(List<NewsfeedDto> newsfeedList) {

    public static NewsfeedListResponseDto createFrom(List<NewsfeedDto> courseDtoList) {
        return new NewsfeedListResponseDto(courseDtoList);
    }

    /**
     * NewsfeedDto 내부 레코드
     */
    public record NewsfeedDto(String imagePath, String content) {
        // Newsfeed 엔터티를 입력받아 DTO 객체 생성 및 반환.
        public static NewsfeedDto createFrom(Newsfeed newsfeed) {
            return new NewsfeedDto(newsfeed.getImagePath(), newsfeed.getContent());
        }
    }
}
