package com.spring.instafeed.profile.dto.response;

import com.spring.instafeed.profile.entity.Profile;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QueryProfileResponseDto {

    private final String nickname;    // 사용자 닉네임
    private final String content;     // 프로필 내용
    private final String imagePath;   // 프로필 이미지 경로

    /**
     * Profile 객체를 QueryProfileResponseDto로 변환하는 static 메서드
     *
     * @param profile 변환할 Profile 엔티티 객체
     * @return Profile을 기반으로 생성된 QueryProfileResponseDto 객체
     */
    public static QueryProfileResponseDto of(Profile profile) {
        return new QueryProfileResponseDto(
                profile.getNickname(),        // 사용자 닉네임
                profile.getContent(),         // 프로필 내용
                profile.getImagePath()        // 프로필 이미지 경로
        );
    }
}