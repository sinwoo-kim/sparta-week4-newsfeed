package com.spring.instafeed.newsfeed.dto.response;

import com.spring.instafeed.newsfeed.entity.Newsfeed;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cglib.core.Local;
import org.springframework.web.multipart.MultipartFile;


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
