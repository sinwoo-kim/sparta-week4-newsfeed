package com.spring.instafeed.user.dto.response;

import com.spring.instafeed.user.entity.User;

public record ReadUserResponseDto(
        Long id,
        String name,
        String email
) {

    /**
     * 생성자
     *
     * @param id    : 사용자 식별자
     * @param name  : 사용자 이름
     * @param email : 사용자 이메일
     */
    public ReadUserResponseDto {
    }

    /**
     * 기능
     * 엔티티를 response DTO로 변환하는 메서드
     *
     * @param user : 엔티티
     * @return UserResponseDto
     */
    public static ReadUserResponseDto toDto(User user) {
        return new ReadUserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}