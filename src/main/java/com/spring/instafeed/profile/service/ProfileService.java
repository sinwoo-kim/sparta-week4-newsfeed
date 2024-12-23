package com.spring.instafeed.profile.service;

import com.spring.instafeed.profile.dto.response.ProfileResponseDto;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    // 프로필 생성
    public ProfileResponseDto createProfile(Long userId, ProfileResponseDto profileResponseDto) {
        // userId로 User 엔티티를 조회
        // User user = userRepository.findById(userId)
        //         .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // ProfileResponseDto에서 Profile 엔티티 생성
        Profile profile = new Profile(
                null,  // id는 null로 설정, 저장 후 자동 생성됨
                profileResponseDto.getNickname(),
                profileResponseDto.getContent(),
                profileResponseDto.getImagePath(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                false,  // 초기값은 false로 설정
                null    // 삭제일자는 null로 설정
        );

        // 프로필 저장
        Profile savedProfile = profileRepository.save(profile);

        // Profile 객체를 ProfileResponseDto로 변환하여 반환
        return ProfileResponseDto.of(savedProfile);
    }

    // 프로필 목록 조회
    @Transactional(readOnly = true)
    public List<ProfileResponseDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll();
        return profiles.stream()
                .map(ProfileResponseDto::of)  // Profile 객체를 DTO로 변환하여 리스트로 반환
                .collect(Collectors.toList());
    }

    // 프로필 수정
    public ProfileResponseDto updateProfile(Long id, ProfileResponseDto updatedProfileDto) {
        Optional<Profile> existingProfileOpt = profileRepository.findById(id);

        if (existingProfileOpt.isPresent()) {
            Profile existingProfile = existingProfileOpt.get();

            // 기존 프로필 데이터를 기반으로 새로운 객체 생성
            Profile updatedProfile = new Profile(
                    existingProfile.getId(),              // 기존 id 유지
                    updatedProfileDto.getNickname(),     // 업데이트된 닉네임
                    updatedProfileDto.getContent(),      // 업데이트된 컨텐츠
                    updatedProfileDto.getImagePath(),    // 업데이트된 이미지 경로
                    existingProfile.getCreatedAt(),      // 기존 createdAt 유지
                    LocalDateTime.now(),                 // 새로운 updatedAt
                    existingProfile.getIsDeleted(),      // 기존 isDeleted 유지
                    existingProfile.getDeletedAt()       // 기존 deletedAt 유지
            );

            // 수정된 프로필 저장
            Profile savedProfile = profileRepository.save(updatedProfile);

            // 수정된 프로필을 DTO로 변환하여 반환
            return ProfileResponseDto.of(savedProfile);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found");
        }
    }
}