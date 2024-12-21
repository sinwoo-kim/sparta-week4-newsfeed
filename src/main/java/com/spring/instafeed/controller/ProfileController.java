package com.spring.instafeed.controller;

import com.spring.instafeed.dto.profile.response.ProfileResponseDto;
import com.spring.instafeed.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    // 프로필 생성 (ResponseDto를 매개변수로 받음)
    @PostMapping
    public ProfileResponseDto createProfile(@RequestBody ProfileResponseDto profileResponseDto) {
        Long userId = 1L; // user클래스 합치고 삭제 처리!!!!
        return profileService.createProfile(userId, profileResponseDto);
    }

    // 프로필 목록 조회
    @GetMapping
    public List<ProfileResponseDto> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    // 프로필 수정
    @PutMapping("/{id}")
    public ProfileResponseDto updateProfile(@PathVariable Long id, @RequestBody ProfileResponseDto updatedProfileDto) {
        return profileService.updateProfile(id, updatedProfileDto);
    }
}