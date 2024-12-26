package com.spring.instafeed.profile.service;

import com.spring.instafeed.profile.dto.response.*;

import java.util.List;

public interface ProfileService {

    CreateProfileResponseDto createProfile(
            Long userId,
            String nickname,
            String content,
            String imagePath
    );

    /**
     * 기능
     * 프로필 목록 조회
     *
     * @return 삭제되지 않은 프로필의 리스트
     */
    List<ReadProfileResponseDto> readAllProfiles();

    ReadProfileResponseDto readProfileById(Long id);

    UpdateProfileResponseDto updateProfile(
            Long id,
            String nickname,
            String content,
            String imagePath

    );

    void deleteProfile(Long id);
}