package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewsfeedListResponseDto {

    //TODO: 페이징을 위해 속성 구성 어떻게 해야되지?
    private String imagePath;

    public NewsfeedListResponseDto(Newsfeed newsfeed) {
        this.imagePath = newsfeed.getImagePath();
    }
}
