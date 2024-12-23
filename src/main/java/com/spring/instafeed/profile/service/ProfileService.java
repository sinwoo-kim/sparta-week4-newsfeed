package com.spring.instafeed.profile.service;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.CreateProfileResponseDto;
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
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;  // Profile 엔티티에 대한 데이터 처리
    private final UserRepository userRepository;        // User 엔티티에 대한 데이터 처리

    /**
     * 프로필 생성
     *
     * 주어진 사용자 ID와 프로필 정보를 바탕으로 새로운 프로필을 생성합니다.
     * 생성된 프로필은 데이터베이스에 저장되고, 저장된 프로필 정보를 DTO로 변환하여 반환합니다.
     *
     * @param userId                   사용자 ID
     * @param createProfileRequestDto  생성할 프로필 정보
     * @return CreateProfileResponseDto 생성된 프로필 정보를 담은 응답 DTO
     * @throws ResponseStatusException 사용자 ID가 null이거나 사용자가 존재하지 않는 경우 예외 발생
     */
    @Transactional
    public CreateProfileResponseDto createProfile(Long userId, CreateProfileRequestDto createProfileRequestDto) {
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must not be null");
        }

        // userId로 User 엔티티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // Profile 엔티티 생성
        Profile profile = new Profile(
                user,                             // 사용자 정보
                createProfileRequestDto.getNickname(),  // 닉네임
                createProfileRequestDto.getContent(),   // 프로필 내용
                createProfileRequestDto.getImagePath()  // 프로필 이미지 경로
        );

        // 프로필 저장
        Profile savedProfile = profileRepository.save(profile);

        // 저장된 프로필 정보를 DTO로 변환하여 반환
        return CreateProfileResponseDto.of(savedProfile);
    }

    /**
     * 모든 프로필 목록 조회
     *
     * 데이터베이스에서 모든 프로필 정보를 조회하고, 이를 DTO로 변환하여 반환합니다.
     *
     * @return List<UpdateProfileResponseDto> 모든 프로필 정보 리스트
     */
    @Transactional(readOnly = true)
    public List<UpdateProfileResponseDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAll(); // 모든 프로필 조회
        return profiles.stream()                             // 프로필 객체를 DTO로 변환
                .map(UpdateProfileResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 프로필 단건 조회
     *
     * 주어진 프로필 ID에 해당하는 프로필을 조회하고, 이를 DTO로 변환하여 반환합니다.
     *
     * @param id 조회할 프로필 ID
     * @return UpdateProfileResponseDto 조회된 프로필 정보
     * @throws ResponseStatusException 프로필이 존재하지 않으면 예외 발생
     */
    @Transactional(readOnly = true)
    public UpdateProfileResponseDto getProfileById(Long id) {
        Profile profile = profileRepository.findById(id)  // ID로 프로필 조회
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 조회된 프로필 객체를 DTO로 변환하여 반환
        return UpdateProfileResponseDto.of(profile);
    }

    /**
     * 프로필 수정
     *
     * 주어진 프로필 ID에 해당하는 프로필을 수정하여 저장합니다.
     * 수정된 프로필 정보는 DTO로 변환되어 반환됩니다.
     *
     * @param id                         수정할 프로필 ID
     * @param updatedProfileRequestDto   수정할 프로필 정보
     * @return UpdateProfileResponseDto  수정된 프로필 정보
     * @throws ResponseStatusException 프로필이 존재하지 않으면 예외 발생
     */
    @Transactional
    public UpdateProfileResponseDto updateProfile(Long id, UpdateProfileRequestDto updatedProfileRequestDto) {
        Profile existingProfile = profileRepository.findById(id)  // ID로 기존 프로필 조회
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 기존 프로필을 업데이트하여 새로운 Profile 객체 생성
        existingProfile = new Profile(
                existingProfile.getUser(),                  // 기존 사용자 유지
                updatedProfileRequestDto.getNickname(),      // 새로운 닉네임
                updatedProfileRequestDto.getContent(),       // 새로운 내용
                updatedProfileRequestDto.getImagePath()      // 새로운 이미지 경로
        );

        // 수정된 프로필 저장
        Profile savedProfile = profileRepository.save(existingProfile);

        // 수정된 프로필을 DTO로 변환하여 반환
        return UpdateProfileResponseDto.of(savedProfile);
    }
}