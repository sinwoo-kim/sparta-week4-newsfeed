package com.spring.instafeed.profile.service;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.CreateProfileResponseDto;
import com.spring.instafeed.profile.dto.response.DeleteProfileResponseDto;
import com.spring.instafeed.profile.dto.response.QueryProfileResponseDto;
import com.spring.instafeed.profile.dto.response.UpdateProfileResponseDto;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;  // Profile 엔티티에 대한 데이터 처리
    private final UserRepository userRepository;        // User 엔티티에 대한 데이터 처리

    /**
     * 새로운 프로필을 생성합니다.
     *
     * @param userId                 프로필을 생성할 사용자의 ID
     * @param createProfileRequestDto 프로필 생성 요청 데이터
     * @return 생성된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 사용자가 존재하지 않거나, 닉네임이 이미 존재할 경우 예외 발생
     */
    public CreateProfileResponseDto createProfile(Long userId, CreateProfileRequestDto createProfileRequestDto) {
        // 사용자 ID로 사용자를 조회합니다.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 닉네임이 이미 존재하는지 확인합니다.
        if (profileRepository.existsByNickname(createProfileRequestDto.nickname())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname already exists");
        }

        // 새로운 프로필 객체를 생성합니다.
        Profile profile = Profile.createFromDto(user, createProfileRequestDto);

        // 프로필을 데이터베이스에 저장합니다.
        Profile savedProfile = profileRepository.save(profile);

        // 저장된 프로필에 대한 응답 데이터 생성 및 반환
        return CreateProfileResponseDto.of(savedProfile);
    }

    /**
     * 모든 프로필을 조회합니다.
     *
     * @return 삭제되지 않은 프로필의 리스트
     */
    public List<QueryProfileResponseDto> getAllProfiles() {
        // 삭제되지 않은 모든 프로필을 조회합니다.
        List<Profile> profiles = profileRepository.findAllByIsDeletedFalse();

        // 프로필 리스트를 응답 DTO 리스트로 변환합니다.
        return profiles.stream()
                .map(QueryProfileResponseDto::of)  // 각 Profile을 QueryProfileResponseDto로 변환
                .collect(Collectors.toList());      // 리스트로 수집
    }

    /**
     * ID로 프로필을 조회합니다.
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 조회된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않을 경우 예외 발생
     */
    public QueryProfileResponseDto getProfileById(Long id) {
        // 주어진 ID로 삭제되지 않은 프로필을 조회합니다.
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 조회된 프로필에 대한 응답 데이터 생성 및 반환
        return QueryProfileResponseDto.of(profile);
    }

    /**
     * 프로필을 수정합니다.
     *
     * @param profileId                   수정할 프로필의 ID
     * @param updateProfileRequestDto     프로필 수정 요청 데이터
     * @return 수정된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않을 경우 예외 발생
     */
    public UpdateProfileResponseDto updateProfile(Long profileId, UpdateProfileRequestDto updateProfileRequestDto) {
        // 주어진 ID로 프로필을 조회합니다.
        Profile existingProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 프로필 정보를 DTO로부터 업데이트합니다.
        existingProfile.updateFromDto(updateProfileRequestDto); // UpdateProfileRequestDto를 사용하여 프로필 정보 업데이트

        // 수정된 프로필에 대한 응답 데이터 생성 및 반환
        return UpdateProfileResponseDto.of(existingProfile); // 기존 프로필 객체를 사용하여 응답 생성
    }

    /**
     * 프로필을 삭제합니다.
     *
     * @param id : 삭제할 프로필의 ID
     * @return 삭제된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않거나 이미 삭제된 경우 예외 발생
     */
    public DeleteProfileResponseDto deleteProfile(Long id) {
        // 주어진 ID로 프로필을 조회합니다.
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 이미 삭제된 프로필인지 확인합니다.
        if (profile.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is already deleted");
        }

        // 프로필을 삭제 처리합니다.
        profile.deleteFromDto();  // 프로필 삭제 처리

        // 삭제된 프로필에 대한 응답 데이터 생성 및 반환
        return DeleteProfileResponseDto.of(profile);
    }
}