package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;
import lombok.Getter;

import java.util.List;

@Getter
public class NewsfeedListResponseDto {

    //TODO: 페이징을 위해 속성 구성 어떻게 해야되지?
    private List<NewsfeedDto> newsfeedList;

    private NewsfeedListResponseDto(List<NewsfeedDto> NewsfeedList) {
        this.newsfeedList = newsfeedList;
    }

    public static NewsfeedListResponseDto createFrom(List<NewsfeedDto> courseDtoList) {
        return new NewsfeedListResponseDto(courseDtoList);
    }

    /**
     * NewsfeedDto 내부 클래스
     */
    @Getter
    public static class NewsfeedDto {
        private String imagePath;
        private String content;

        private NewsfeedDto(Newsfeed newsfeed ){
            this.imagePath = newsfeed.getImagePath();
            this.content = newsfeed.getContent();
        }
        // Newsfeed 엔터티를 입력받아 DTO 객체 생성 및 반환.
        public static NewsfeedDto createFrom(Newsfeed newsfeed) {
            return new NewsfeedDto(newsfeed);
        }
    }
}
