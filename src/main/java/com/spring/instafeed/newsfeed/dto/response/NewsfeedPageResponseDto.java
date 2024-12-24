package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;

public record NewsfeedPageResponseDto(Long id, String nickname, String imagePath, String content) {

    /**
     * NewsfeedDto 내부 레코드
     */
    public record NewsfeedDto(Long id, String imagePath, String content) {
        // Newsfeed 엔터티를 입력받아 DTO 객체 생성 및 반환.
        public static NewsfeedDto createFrom(Newsfeed newsfeed) {
            return new NewsfeedDto(newsfeed.getNewsfeedId(), newsfeed.getImagePath(), newsfeed.getContent());
        }
    }

    /**
     *
     * @param id
     * @param nickname
     * @param imagePath
     * @param content
     * @return
     */
    public static NewsfeedPageResponseDto of(Long id, String nickname, String imagePath, String content) {
        return new NewsfeedPageResponseDto(id, nickname, imagePath, content);
    }

}
