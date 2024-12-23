package com.spring.instafeed.user.dto.request;

import lombok.Getter;

@Getter
public class SignUpUserRequestDto {

    // 속성
    private final String name;
    private final String email;
    private final String password;

    /**
     * 생성자
     *
     * @param name     : 사용자 이름
     * @param email    : 사용자 이메일
     * @param password : 사용자 비밀번호
     */
    public SignUpUserRequestDto(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}