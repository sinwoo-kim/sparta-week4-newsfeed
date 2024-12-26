package com.spring.instafeed.profile.controller;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.*;
import com.spring.instafeed.profile.service.ProfileServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    // 속성
    private final ProfileServiceImpl profileService;

    /**
     * 기능
     * 프로필 생성
     *
     * @param requestDto : 프로필 생성 요청 DTO (userId 포함)
     * @return CreateProfileResponseDto
     */
    @PostMapping
    public ResponseEntity<CreateProfileResponseDto> createProfile(
            @RequestBody CreateProfileRequestDto requestDto
    ) {
        CreateProfileResponseDto responseDto = profileService
                .createProfile(
                        requestDto.userId(),
                        requestDto.nickname(),
                        requestDto.content(),
                        requestDto.imagePath()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    /**
     * 기능
     * 프로필 목록 조회
     *
     * @return List<QueryProfileResponseDto> 모든 프로필 정보를 담은 리스트
     */
    @GetMapping
    public ResponseEntity<List<ReadProfileResponseDto>> readAllProfiles() {
        List<ReadProfileResponseDto> allProfiles = new ArrayList<>();

        allProfiles = profileService.readAllProfiles();

        // todo 깡통 리스트 나오지 않도록 리팩토링 필요
        return new ResponseEntity<>(allProfiles, HttpStatus.OK);
    }

    /**
     * 프로필 단건 조회
     * 프로필 ID를 바탕으로 특정 프로필을 조회하는 엔드포인트.
     * 주어진 ID에 해당하는 프로필 정보를 반환한다.
     *
     * @param request : 조회할 프로필의 user 정보를 포함한 HttpServletRequest
     * @return 조회된 프로필 정보를 담은 Response DTO
     */
    @GetMapping
    public ResponseEntity<ReadProfileResponseDto> readProfileById(
            HttpServletRequest request
    ) {
        Long id = (Long) request.getAttribute("userId");

        ReadProfileResponseDto profile = profileService
                .readProfileById(id);

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    /**
     * 기능
     * 프로필 전체 수정
     *
     * @param request : user 정보를 포함한 HttpServletRequest
     * @param requestDto : 수정할 프로필 정보를 담은 요청 DTO
     * @return 수정된 프로필 정보를 담은 응답 DTO
     */
    @PutMapping
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(
            HttpServletRequest request,
            @RequestBody UpdateProfileRequestDto requestDto
    ) {
        Long id = (Long) request.getAttribute("userId");

        UpdateProfileResponseDto responseDto = profileService
                .updateProfile(
                        id,
                        requestDto.nickname(),
                        requestDto.content(),
                        requestDto.imagePath()
                );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 프로필 삭제
     * 특정 프로필을 논리적으로 삭제하는 엔드포인트입니다.
     *
     * @param request : 삭제할 프로필의 user 정보를 포함한 HttpServletRequest
     * @return 삭제된 프로필 정보
     */
    @DeleteMapping
    public ResponseEntity<Void> deleteProfile(
            HttpServletRequest request
    ) {
        Long id = (Long) request.getAttribute("userId");

        profileService.deleteProfile(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}