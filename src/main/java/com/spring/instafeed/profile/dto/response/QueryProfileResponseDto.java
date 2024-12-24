package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;

public record QueryProfileResponseDto(
        String nickname,
        String content,
        String imagePath
) {
    /**
     * Profile 객체를 QueryProfileResponseDto로 변환하는 static 메서드
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile을 기반으로 생성된 QueryProfileResponseDto 객체
     */
    public static QueryProfileResponseDto of(Profile profile) {
        return new QueryProfileResponseDto(
                profile.getNickname(),
                profile.getContent(),
                profile.getImagePath()
        );
    }
}