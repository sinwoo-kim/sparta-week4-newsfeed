package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class DeleteProfileResponseDto {

    private final Long id;
    private final Long userId;
    private final String nickname;
    private final String content;
    private final String imagePath;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Boolean isDeleted;
    private final LocalDateTime deletedAt;

    /**
     * Profile 객체를 DeleteProfileResponseDto로 변환하는 static 메서드
     *
     * 이 메서드는 주어진 Profile 엔티티 객체를 DeleteProfileResponseDto 객체로 변환하여 반환합니다.
     * 주로 응답 데이터 포맷으로 변환할 때 사용됩니다.
     * 변환된 DTO 객체는 클라이언트에게 전달될 JSON 형태로 변환되어 응답에 포함됩니다.
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile을 기반으로 생성된 DeleteProfileResponseDto 객체
     */
    public static DeleteProfileResponseDto of(Profile profile) {
        return new DeleteProfileResponseDto(
                profile.getId(),                                // 프로필 ID
                profile.getUser() != null ? profile.getUser().getId() : null,  // 사용자 ID (User가 null일 경우 null 처리)
                profile.getNickname(),                          // 사용자 닉네임
                profile.getContent(),                           // 프로필 내용
                profile.getImagePath(),                         // 프로필 이미지 경로
                profile.getCreatedAt(),                         // 프로필 생성 일자
                profile.getUpdatedAt(),                         // 프로필 수정 일자
                profile.getIsDeleted(),                        // 프로필 삭제 여부
                profile.getDeletedAt()                          // 프로필 삭제 일자
        );
    }
}
