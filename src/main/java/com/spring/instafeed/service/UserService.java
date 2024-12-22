package com.spring.instafeed.service;

import com.spring.instafeed.dto.user.response.UserResponseDto;

public interface UserService {
    /**
     * 기능
     * 회원가입
     *
     * @param name : 사용자 이름
     * @param email    : 사용자 이메일
     * @param password : 사용자 비밀번호
     * @return UserResponseDto
     */
    UserResponseDto signUp(String name, String email, String password);

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 식별자
     * @return UserResponseDto
     */
    UserResponseDto findById(Long id);
}
