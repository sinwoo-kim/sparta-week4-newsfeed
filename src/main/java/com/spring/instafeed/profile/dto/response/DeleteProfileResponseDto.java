package com.spring.instafeed.profile.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.spring.instafeed.profile.entity.Profile;

import java.time.LocalDateTime;

public record DeleteProfileResponseDto(
        Long id,
        String nickname,
        String imagePath,
        String content,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        Boolean isDeleted,
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime deletedAt
) {

    /**
     * Profile 객체를 DeleteProfileResponseDto로 변환하는 static 메서드
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile을 기반으로 생성된 DeleteProfileResponseDto 객체
     */
    public static DeleteProfileResponseDto toDto(Profile profile) {
        return new DeleteProfileResponseDto(
                profile.getId(),
                profile.getNickname(),
                profile.getImagePath(),
                profile.getContent(),
                profile.getCreatedAt(),
                profile.getUpdatedAt(),
                profile.getIsDeleted(),
                profile.getDeletedAt()
        );
    }
}