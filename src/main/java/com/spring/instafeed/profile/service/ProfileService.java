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

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
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
    @Transactional
    public CreateProfileResponseDto createProfile(Long userId, CreateProfileRequestDto createProfileRequestDto) {

        // 사용자 ID가 null인지 확인
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must not be null");
        }
        // 필요한 부분인지 검증 필요

        // 주어진 userId로 User 엔티티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 닉네임이 이미 존재하는지 확인합니다.
        if (profileRepository.existsByNickname(createProfileRequestDto.nickname())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname already exists");
        }

        // 새로운 프로필 객체를 생성합니다.
        Profile profile = new Profile(
                user,
                createProfileRequestDto.nickname(),
                createProfileRequestDto.content(),
                createProfileRequestDto.imagePath()
        );

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
    @Transactional(readOnly = true)
    public List<QueryProfileResponseDto> getAllProfiles() {

//        List<Profile> profiles = profileRepository.findAllActiveProfiles(); // 삭제되지 않은 프로필만 조회
//        return profiles.stream()                             // 프로필 객체를 DTO로 변환
//                .map(QueryProfileResponseDto::of).toList();
        return null;
    }

    /**
     * ID로 프로필을 조회합니다.
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 조회된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않을 경우 예외 발생
     */
    @Transactional(readOnly = true)
    public QueryProfileResponseDto getProfileById(Long id) {
        // 주어진 ID로 삭제되지 않은 프로필을 조회합니다.
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 조회된 프로필에 대한 응답 데이터 생성 및 반환
        return new QueryProfileResponseDto(
                profile.getNickname(),
                profile.getContent(),
                profile.getImagePath()
        );
    }

    /**
     * 프로필을 수정합니다.
     *
     * @param profileId                   수정할 프로필의 ID
     * @param updateProfileRequestDto     프로필 수정 요청 데이터
     * @return 수정된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않을 경우 예외 발생
     */
    @Transactional
    public UpdateProfileResponseDto updateProfile(Long profileId, UpdateProfileRequestDto updateProfileRequestDto) {
        // 주어진 ID로 프로필을 조회합니다.
        Profile existingProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 프로필 정보를 업데이트합니다.
        existingProfile.updateProfile(
                updateProfileRequestDto.nickname(),
                updateProfileRequestDto.content(),
                updateProfileRequestDto.imagePath()
        );


        // 수정된 프로필에 대한 응답 데이터 생성 및 반환
        return new UpdateProfileResponseDto(
                existingProfile.getId(),
                existingProfile.getUser().getId(),
                existingProfile.getNickname(),
                existingProfile.getContent(),
                existingProfile.getImagePath(),
                existingProfile.getCreatedAt(),
                existingProfile.getUpdatedAt()
        );
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
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 이미 삭제된 프로필인지 확인합니다.
        if (profile.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is already deleted");
        }

        // 프로필을 삭제 처리합니다.
        profile.markAsDeleted();

        // 삭제된 프로필에 대한 응답 데이터 생성 및 반환
        return new DeleteProfileResponseDto(
                profile.getId(),
                profile.getUser() != null ? profile.getUser().getId() : null,
                profile.getNickname(),
                profile.getContent(),
                profile.getImagePath(),
                profile.getCreatedAt(),
                profile.getUpdatedAt(),
                profile.getIsDeleted(),
                profile.getDeletedAt()
        );

        // 삭제된 프로필 저장
        Profile deletedProfile = profileRepository.save(profile);

        // 삭제된 프로필을 DTO로 변환하여 반환
        return DeleteProfileResponseDto.of(deletedProfile);

    }
}