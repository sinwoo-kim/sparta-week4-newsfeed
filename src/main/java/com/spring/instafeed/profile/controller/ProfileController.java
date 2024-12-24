package com.spring.instafeed.profile.controller;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.*;
import com.spring.instafeed.profile.service.ProfileServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfileController {

    // 속성
    private final ProfileServiceImpl profileServiceImpl;

    /**
     * 기능
     * 프로필 생성
     *
     * @param requestDto : 프로필 생성 요청 DTO (userId 포함)
     * @return createProfileResponseDto 생성된 프로필 정보를 담은 응답 DTO
     * @throws ResponseStatusException : 사용자 ID가 null이거나, 사용자가 존재하지 않으면 예외 발생
     * @throws ResponseStatusException : 닉네임이 중복되는 경우 예외 발생
     */
    @PostMapping
    public ResponseEntity<CreateProfileResponseDto> createProfile(
            @RequestBody CreateProfileRequestDto requestDto
    ) {
        CreateProfileResponseDto responseDto = profileServiceImpl.createProfile(
                requestDto.userId(),
                requestDto
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

        allProfiles = profileServiceImpl.readAllProfiles();

        // todo 깡통 리스트 나오지 않도록 리팩토링 필요
        return new ResponseEntity<>(allProfiles, HttpStatus.OK);
    }

    /**
     * 프로필 단건 조회
     * <p>
     * 프로필 ID를 바탕으로 특정 프로필을 조회하는 엔드포인트.
     * 주어진 ID에 해당하는 프로필 정보를 반환한다.
     *
     * @param id 조회할 프로필의 ID
     * @return 조회된 프로필 정보를 담은 Response DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<ReadProfileResponseDto> findById(
            @PathVariable("id") Long id
    ) {
        ReadProfileResponseDto profile = profileServiceImpl.findById(id);

        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    /**
     * 기능
     * 프로필 전체 수정
     *
     * @param id         프로필 ID (URL 경로에서 전달받음)
     * @param requestDto 수정할 프로필 정보를 담은 요청 DTO
     * @return 수정된 프로필 정보를 담은 응답 DTO
     */
    @PutMapping("/{id}")
    public ResponseEntity<UpdateProfileResponseDto> updateProfile(
            @PathVariable("id") Long id,
            @RequestBody UpdateProfileRequestDto requestDto
    ) {

        UpdateProfileResponseDto responseDto = profileServiceImpl.updateProfile(
                id,
                requestDto
        );
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    /**
     * 프로필 삭제
     * 특정 프로필을 논리적으로 삭제하는 엔드포인트입니다.
     *
     * @param id 삭제할 프로필의 ID
     * @return 삭제된 프로필 정보
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProfile(
            @PathVariable("id") Long id
    ) {
        profileServiceImpl.deleteProfile(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}