package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class UpdateProfileResponseDto {

    private final Long id;
    private final Long userId;
    private final String nickname;
    private final String content;
    private final String imagePath;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    /**
     * Profile 객체를 UpdateProfileResponseDto로 변환하는 static 메서드
     *
     * 이 메서드는 주어진 Profile 엔티티 객체를 UpdateProfileResponseDto 객체로 변환하여 반환합니다.
     * 주로 응답 데이터 포맷으로 변환할 때 사용됩니다.
     * 변환된 DTO 객체는 클라이언트에게 전달될 JSON 형태로 변환되어 응답에 포함됩니다.
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile을 기반으로 생성된 UpdateProfileResponseDto 객체
     */
    public static UpdateProfileResponseDto of(Profile profile) {
        return new UpdateProfileResponseDto(
                profile.getId(),                                // 프로필 ID
                profile.getUser() != null ? profile.getUser().getId() : null,  // 사용자 ID (User가 null일 경우 null 처리)
                profile.getNickname(),                          // 사용자 닉네임
                profile.getContent(),                           // 프로필 내용
                profile.getImagePath(),                         // 프로필 이미지 경로
                profile.getCreatedAt(),                         // 프로필 생성 일자
                profile.getUpdatedAt()                          // 프로필 수정 일자
        );
    }
}