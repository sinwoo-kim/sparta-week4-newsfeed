package com.spring.instafeed.profile.controller;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.CreateProfileResponseDto;
import com.spring.instafeed.profile.dto.response.DeleteProfileResponseDto;
import com.spring.instafeed.profile.dto.response.UpdateProfileResponseDto;
import com.spring.instafeed.profile.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 생성
     *
     * 요청 본문에서 `userId`와 프로필 정보를 바탕으로 새로운 프로필을 생성합니다.
     * 생성된 프로필은 데이터베이스에 저장되고, 저장된 프로필 정보를 DTO로 변환하여 반환합니다.
     *
     * @param createProfileRequestDto  프로필 생성 요청 DTO (userId 포함)
     * @return createProfileResponseDto 생성된 프로필 정보를 담은 응답 DTO
     * @throws ResponseStatusException 사용자 ID가 null이거나, 사용자가 존재하지 않으면 예외 발생
     * @throws ResponseStatusException 닉네임이 중복되는 경우 예외 발생
     */
    @PostMapping
    public CreateProfileResponseDto createProfile(
            @RequestBody CreateProfileRequestDto createProfileRequestDto) {  // 요청 본문에서 프로필 정보 받음

        // userId를 요청 본문에서 받아서 서비스 메서드에 전달
        return profileService.createProfile(createProfileRequestDto.getUserId(), createProfileRequestDto);  // userId와 DTO 전달
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
     * 프로필 수정 API
     *
     * 주어진 프로필 ID를 이용하여 프로필 정보를 수정합니다.
     * 프로필이 존재하지 않으면 예외가 발생합니다.
     *
     * @param id 프로필 ID (URL 경로에서 전달받음)
     * @param updatedProfileRequestDto 수정할 프로필 정보를 담은 요청 DTO
     * @return 수정된 프로필 정보를 담은 응답 DTO
     */
    @PutMapping("/{profileId}")
    public UpdateProfileResponseDto updateProfile(
            @PathVariable("profileId") Long id,  // URL에서 사용자 ID를 받음
            @RequestBody UpdateProfileRequestDto updatedProfileRequestDto) {  // 수정할 프로필 정보 받음
        return profileService.updateProfile(id, updatedProfileRequestDto);  // 매개변수 이름을 전달
    }


    /**
     * 프로필 삭제
     * 특정 프로필을 논리적으로 삭제하는 엔드포인트입니다.
     *
     * @param id 삭제할 프로필의 ID
     * @return 삭제된 프로필 정보
     */
    @DeleteMapping("/{id}")
    public DeleteProfileResponseDto deleteProfile(@PathVariable Long id) {
        return profileService.deleteProfile(id);
    }
}