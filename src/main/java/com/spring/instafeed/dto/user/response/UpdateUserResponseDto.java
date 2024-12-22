package com.spring.instafeed.dto.user.response;

import lombok.Getter;

@Getter
public class UpdateUserResponseDto {
    private final String success;

    /**
     * 생성자
     * @param success : 비밀번호 수정이 성공했다는 메시지
     */
    public UpdateUserResponseDto(String success) {
        this.success = success;
    }
}
