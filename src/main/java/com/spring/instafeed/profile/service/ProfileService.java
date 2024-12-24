package com.spring.instafeed.profile.service;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.*;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    // 속성
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    /**
     * 기능
     * 프로필 생성
     *
     * @param userId     : 프로필을 생성할 사용자의 Id
     * @param requestDto : 프로필 생성 요청 데이터
     * @return 생성된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException : 사용자가 존재하지 않거나 닉네임이 이미 존재하면 예외 발생
     */
    @Transactional
    public CreateProfileResponseDto createProfile(
            Long userId,
            CreateProfileRequestDto requestDto
    ) {
        // todo
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "User not found"
                        )
                );

        // todo 닉네임이 이미 존재하는지 확인합니다.
        boolean existsNickname = profileRepository.existsByNickname(
                requestDto.nickname()
        );

        if (existsNickname) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Nickname already exists"
            );
        }

        // 새로운 프로필 객체를 생성합니다.
        Profile profileToSave = Profile.createFromDto(user, requestDto);

        // 프로필을 데이터베이스에 저장합니다.
        Profile savedProfile = profileRepository.save(profileToSave);

        // 저장된 프로필에 대한 응답 데이터 생성 및 반환
        return CreateProfileResponseDto.toDto(savedProfile);
    }

    /**
     * 기능
     * 프로필 목록 조회
     *
     * @return 삭제되지 않은 프로필의 리스트
     */
    public List<ReadProfileResponseDto> readAllProfiles() {
        List<Profile> profiles = new ArrayList<>();

        profiles = profileRepository.findAllByIsDeletedFalse();

        List<ReadProfileResponseDto> allProfiles = new ArrayList<>();

        allProfiles = profiles.stream()
                .map(ReadProfileResponseDto::toDto)
                .toList();

        // 프로필 리스트를 응답 DTO 리스트로 변환합니다.
        return allProfiles;  // 리스트로 수집
    }

    /**
     * ID로 프로필을 조회합니다.
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 조회된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않으면 예외 발생
     */
    public ReadProfileResponseDto readEachProfile(Long id) {
        // 주어진 ID로 삭제되지 않은 프로필 조회
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Profile not found"
                        )
                );

        return ReadProfileResponseDto.toDto(profile);
    }

    /**
     * 프로필을 수정합니다.
     *
     * @param id         수정할 프로필의 ID
     * @param requestDto 프로필 수정 요청 데이터
     * @return 수정된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않을 경우 예외 발생
     */
    @Transactional
    public UpdateProfileResponseDto updateProfile(
            Long id,
            UpdateProfileRequestDto requestDto
    ) {
        // 주어진 ID로 프로필을 조회합니다.
        Profile profile = profileRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Profile not found"
                        )
                );

        // 프로필 정보를 DTO로부터 업데이트
        profile.updateFromDto(requestDto);

        // 수정된 프로필에 대한 응답 데이터 생성 및 반환
        return UpdateProfileResponseDto.toDto(profile);
    }

    /**
     * 프로필을 삭제합니다.
     *
     * @param id : 삭제할 프로필의 ID
     * @return 삭제된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않거나 이미 삭제된 경우 예외 발생
     */
    @Transactional
    public DeleteProfileResponseDto deleteProfile(Long id) {
        // 주어진 ID로 프로필을 조회합니다.
        Profile profile = profileRepository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND, "Profile not found"
                        )
                );

        // 이미 삭제된 프로필인지 확인합니다.
        if (profile.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Profile is already deleted"
            );
        }

        // 프로필을 삭제 처리합니다.
        profile.deleteFromDto();  // 프로필 삭제 처리

        // 삭제된 프로필에 대한 응답 데이터 생성 및 반환
        return DeleteProfileResponseDto.toDto(profile);
    }
}