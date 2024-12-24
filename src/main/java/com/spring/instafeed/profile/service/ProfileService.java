package com.spring.instafeed.profile.service;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.CreateProfileResponseDto;
import com.spring.instafeed.profile.dto.response.DeleteProfileResponseDto;
import com.spring.instafeed.profile.dto.response.ReadProfileResponseDto;
import com.spring.instafeed.profile.dto.response.UpdateProfileResponseDto;

import java.util.List;

public interface ProfileService {

    CreateProfileResponseDto createProfile(
            Long userId,
            CreateProfileRequestDto requestDto
    );

    /**
     * 기능
     * 프로필 목록 조회
     *
     * @return 삭제되지 않은 프로필의 리스트
     */
    List<ReadProfileResponseDto> readAllProfiles();


    ReadProfileResponseDto findById(Long id);


    UpdateProfileResponseDto updateProfile(
            Long id,
            UpdateProfileRequestDto requestDto
    );

    void deleteProfile(Long id);
}
