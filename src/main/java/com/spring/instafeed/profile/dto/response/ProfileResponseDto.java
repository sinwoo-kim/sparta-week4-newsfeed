package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ProfileResponseDto {

    private final Long id;
    //private final Long userId;
    private final String nickname;
    private final String content;
    private final String imagePath;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean isDeleted;
    private final LocalDateTime deletedAt;

    // Profile 객체를 ProfileResponseDto로 변환하는 static 메서드(생성용)
    public static ProfileResponseDto of(Profile profile) {
        return new ProfileResponseDto(
                profile.getId(),
                //profile.getUser() != null ? profile.getUser().getId() : null,
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
