package com.spring.instafeed.profile.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateProfileRequestDto {

    @NotNull(message = "닉네임은 필수입니다.")
    private String nickname;
    private String content;
    private String imagePath;
    private Long userId;

}
