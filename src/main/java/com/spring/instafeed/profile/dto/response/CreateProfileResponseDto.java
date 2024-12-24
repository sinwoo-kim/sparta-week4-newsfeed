package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;

import java.time.LocalDateTime;

public record CreateProfileResponseDto(
        Long id,
        Long userId,
        String nickname,
        String content,
        String imagePath,
        LocalDateTime createdAt
) {
    /**
     * Profile 객체를 CreateProfileResponseDto로 변환하는 static 메서드
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile을 기반으로 생성된 CreateProfileResponseDto 객체
     */
    public static CreateProfileResponseDto of(Profile profile) {
        return new CreateProfileResponseDto(
                profile.getId(),                                // 프로필 ID
                profile.getUser() != null ? profile.getUser().getId() : null,  // 사용자 ID
                profile.getNickname(),                          // 사용자 닉네임
                profile.getContent(),                           // 프로필 내용
                profile.getImagePath(),                         // 프로필 이미지 경로
                profile.getCreatedAt()                          // 프로필 생성 일자
        );
    }
}
