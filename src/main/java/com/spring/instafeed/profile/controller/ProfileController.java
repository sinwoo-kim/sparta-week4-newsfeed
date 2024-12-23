package com.spring.instafeed.profile.controller;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.CreateProfileResponseDto;
import com.spring.instafeed.profile.dto.response.UpdateProfileResponseDto;
import com.spring.instafeed.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    /**
     * ProfileService 객체
     *
     * 프로필 생성, 조회, 수정 등 실제 비즈니스 로직을 처리하는 서비스 클래스.
     */
    private final ProfileService profileService;

    /**
     * 프로필 생성
     *
     * POST 요청을 통해 새로운 프로필을 생성하는 엔드포인트.
     * 요청 본문에서 받은 사용자 ID와 프로필 정보를 바탕으로 새로운 프로필을 생성한다.
     *
     * @param createProfileRequestDto 프로필 생성 요청 DTO
     * @return 생성된 프로필 정보를 담은 Response DTO
     */
    @PostMapping
    public CreateProfileResponseDto createProfile(@RequestBody CreateProfileRequestDto createProfileRequestDto) {
        return profileService.createProfile(createProfileRequestDto.getUserId(), createProfileRequestDto);
    }

    /**
     * 프로필 목록 조회
     *
     * GET 요청을 통해 모든 프로필 목록을 조회하는 엔드포인트.
     * 저장된 모든 프로필 정보를 `UpdateProfileResponseDto` 형식으로 반환한다.
     *
     * @return 모든 프로필 정보를 담은 리스트
     */
    @GetMapping
    public List<UpdateProfileResponseDto> getAllProfiles() {
        return profileService.getAllProfiles();
    }

    /**
     * 프로필 단건 조회
     *
     * 프로필 ID를 바탕으로 특정 프로필을 조회하는 엔드포인트.
     * 주어진 ID에 해당하는 프로필 정보를 반환한다.
     *
     * @param id 조회할 프로필의 ID
     * @return 조회된 프로필 정보를 담은 Response DTO
     */
    @GetMapping("/{id}")
    public UpdateProfileResponseDto getProfileById(@PathVariable Long id) {
        return profileService.getProfileById(id);
    }

    /**
     * 프로필 수정
     *
     * PUT 요청을 통해 특정 프로필을 수정하는 엔드포인트.
     * 주어진 프로필 ID와 수정된 내용을 바탕으로 프로필 정보를 업데이트한다.
     *
     * @param id 수정할 프로필의 ID
     * @param updatedProfileRequestDto 수정된 프로필 정보 DTO
     * @return 수정된 프로필 정보를 담은 Response DTO
     */
    @PutMapping("/{id}")
    public UpdateProfileResponseDto updateProfile(@PathVariable Long id, @RequestBody UpdateProfileRequestDto updatedProfileRequestDto) {
        return profileService.updateProfile(id, updatedProfileRequestDto);
    }
}