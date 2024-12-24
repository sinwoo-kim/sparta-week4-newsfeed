package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;

import java.time.LocalDateTime;

public record DeleteProfileResponseDto(
        Long id,
        String nickname,
        String content,
        String imagePath,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Boolean isDeleted,
        LocalDateTime deletedAt
) {

    /**
     * Profile 객체를 DeleteProfileResponseDto로 변환하는 static 메서드
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile을 기반으로 생성된 DeleteProfileResponseDto 객체
     */
    public static DeleteProfileResponseDto of(Profile profile) {
        return new DeleteProfileResponseDto(
                profile.getId(),
                profile.getNickname(),
                profile.getContent(),
                profile.getImagePath(),
                profile.getCreatedAt(),
                profile.getUpdatedAt(),
                profile.getIsDeleted(),
                profile.getDeletedAt()
        );
    }
}