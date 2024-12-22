package com.spring.instafeed.service;

import com.spring.instafeed.dto.user.response.UpdateUserResponseDto;
import com.spring.instafeed.dto.user.response.UserResponseDto;

public interface UserService {
    /**
     * 기능
     * 회원가입
     *
     * @param name     : 사용자 이름
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

    /**
     * 기능
     * 비밀번호 수정
     *
     * @param id       : 비밀번호를 수정하려는 사용자의 식별자
     * @param password : 수정하려는 비밀번호
     * @return UpdateUserResponseDto
     */
    UpdateUserResponseDto updatePasswordById(Long id, String password);

    /**
     * 기능
     *
     * @param id : 삭제하려는 사용자의 식별자
     */
    void delete(Long id);
}