package com.spring.instafeed.user.service;

import com.spring.instafeed.user.dto.response.UpdateUserResponseDto;
import com.spring.instafeed.user.dto.response.ReadUserResponseDto;

public interface UserService {

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 식별자
     * @return UserResponseDto
     */
    ReadUserResponseDto readUserById(Long id);


    /**
     * 기능
     * 비밀번호 수정
     *
     * @param id          : 비밀번호를 수정하려는 사용자의 식별자
     * @param oldPassword : 기존의 비밀번호
     * @param newPassword : 새로운 비밀번호
     * @return UpdateUserResponseDto
     */
    UpdateUserResponseDto updatePassword(
            Long id,
            String oldPassword,
            String newPassword
    );

    /**
     * 기능
     * 사용자 탈퇴
     * @param id : 탈퇴하려는 사용자의 식별자
     * @param password : 탈퇴 전 입력해야 하는 비밀번호
     */
    void deleteUser(
            Long id,
            String password
    );
}