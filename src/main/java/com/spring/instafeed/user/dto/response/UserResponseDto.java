package com.spring.instafeed.user.dto.response;

import com.spring.instafeed.user.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    // 속성
    private final Long id;
    private final String name;
    private final String email;

    /**
     * 생성자
     *
     * @param id    : 사용자 식별자
     * @param name  : 사용자 이름
     * @param email : 사용자 이메일
     */
    public UserResponseDto(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /**
     * 기능
     * 엔티티를 response DTO로 변환하는 메서드
     *
     * @param user : 엔티티
     * @return UserResponseDto
     */
    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(user.getId(), user.getName(), user.getEmail());
    }
}
