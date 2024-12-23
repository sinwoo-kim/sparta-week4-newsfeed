package com.spring.instafeed.user.dto.request;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    // 속성
    private final String password;

    /**
     * 생성자
     *
     * @param password : 수정하려는 비밀번호
     */
    public UpdateUserRequestDto(String password) {
        this.password = password;
    }
}