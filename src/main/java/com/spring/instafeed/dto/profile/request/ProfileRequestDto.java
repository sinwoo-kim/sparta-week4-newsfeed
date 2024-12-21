package com.spring.instafeed.dto.profile.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class ProfileRequestDto {

    @NotNull(message = "닉네임은 필수입니다.")
    private String nickname;
    private String content;
    private String imagePath;

}
