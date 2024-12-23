package com.spring.instafeed.profile.service;

import com.spring.instafeed.profile.dto.request.CreateProfileRequestDto;
import com.spring.instafeed.profile.dto.request.UpdateProfileRequestDto;
import com.spring.instafeed.profile.dto.response.CreateProfileResponseDto;
import com.spring.instafeed.profile.dto.response.DeleteProfileResponseDto;
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
     * 프로필 생성
     *
     * 주어진 사용자 ID와 프로필 정보를 바탕으로 새로운 프로필을 생성합니다.
     * 생성된 프로필은 데이터베이스에 저장되며, 저장된 프로필 정보를 DTO 형식으로 변환하여 반환합니다.
     *
     * @param userId                   사용자 ID
     * @param createProfileRequestDto  프로필 생성 요청 DTO
     * @return CreateProfileResponseDto 생성된 프로필 정보를 담은 응답 DTO
     * @throws ResponseStatusException 사용자 ID가 null이거나, 사용자가 존재하지 않으면 예외 발생
     * @throws ResponseStatusException 닉네임이 중복되는 경우 예외 발생
     */
    @Transactional
    public CreateProfileResponseDto createProfile(Long userId, CreateProfileRequestDto createProfileRequestDto) {
        // 사용자 ID가 null인지 확인
        if (userId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User ID must not be null");
        }

        // 주어진 userId로 User 엔티티를 조회
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        // 입력받은 닉네임이 이미 다른 프로필에서 사용 중인지 확인
        if (profileRepository.existsByNickname(createProfileRequestDto.getNickname())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nickname already exists");
        }

        // 위의 검증을 모두 통과하면, 새로운 프로필 객체를 생성
        Profile profile = new Profile(
                user,                             // 프로필에 해당하는 사용자 정보
                createProfileRequestDto.getNickname(),  // 프로필에 설정할 닉네임
                createProfileRequestDto.getContent(),   // 프로필에 설정할 내용
                createProfileRequestDto.getImagePath()  // 프로필에 설정할 이미지 경로
        );

        // 생성된 프로필 객체를 데이터베이스에 저장
        Profile savedProfile = profileRepository.save(profile);

        // 저장된 프로필 정보를 DTO로 변환하여 응답
        return CreateProfileResponseDto.of(savedProfile);
    }

    /**
     * 모든 프로필 목록 조회
     *
     * 데이터베이스에서 모든 프로필 정보를 조회하고, 이를 DTO로 변환하여 반환합니다.
     * 이미 삭제된 프로필은 조회되지 않습니다.
     *
     * @return List<UpdateProfileResponseDto> 모든 프로필 정보 리스트
     */
    @Transactional(readOnly = true)
    public List<UpdateProfileResponseDto> getAllProfiles() {
        List<Profile> profiles = profileRepository.findAllActiveProfiles(); // 삭제되지 않은 프로필만 조회
        return profiles.stream()                             // 프로필 객체를 DTO로 변환
                .map(UpdateProfileResponseDto::of)
                .collect(Collectors.toList());
    }

    /**
     * 프로필 단건 조회
     *
     * 주어진 프로필 ID에 해당하는 프로필을 조회하고, 이를 DTO로 변환하여 반환합니다.
     * 이미 삭제된 프로필은 조회되지 않습니다.
     *
     * @param id 조회할 프로필 ID
     * @return UpdateProfileResponseDto 조회된 프로필 정보
     * @throws ResponseStatusException 프로필이 존재하지 않으면 예외 발생
     */
    @Transactional(readOnly = true)
    public UpdateProfileResponseDto getProfileById(Long id) {
        Profile profile = profileRepository.findByIdAndIsDeletedFalse(id)  // ID로 프로필 조회, 삭제된 프로필은 제외
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 조회된 프로필 객체를 DTO로 변환하여 반환
        return UpdateProfileResponseDto.of(profile);
    }

    /**
     * 프로필 수정
     *
     * 주어진 프로필 ID에 해당하는 프로필을 수정합니다.
     * 수정된 프로필 정보는 DTO 형태로 변환되어 반환됩니다.
     * 프로필이 존재하지 않거나, 수정된 프로필에 대한 권한이 없으면 예외가 발생합니다.
     *
     * @param profileId                 수정할 프로필 ID
     * @param updatedProfileRequestDto  수정할 프로필 정보 (닉네임, 내용, 이미지 경로 등)
     * @return UpdateProfileResponseDto 수정된 프로필 정보를 담은 응답 DTO
     * @throws ResponseStatusException 프로필이 존재하지 않거나, 사용자 권한이 일치하지 않는 경우 예외 발생
     */
    @Transactional
    public UpdateProfileResponseDto updateProfile(Long profileId, UpdateProfileRequestDto updatedProfileRequestDto) {
        // 프로필 ID로 기존 프로필을 조회
        Profile existingProfile = profileRepository.findById(profileId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 프로필의 내용을 수정
        existingProfile.setNickname(updatedProfileRequestDto.getNickname());
        existingProfile.setContent(updatedProfileRequestDto.getContent());
        existingProfile.setImagePath(updatedProfileRequestDto.getImagePath());

        // 수정된 프로필 객체를 데이터베이스에 저장
        Profile savedProfile = profileRepository.save(existingProfile);

        // 수정된 프로필을 DTO로 변환하여 반환
        return UpdateProfileResponseDto.of(savedProfile);
    }


    /**
     * 프로필을 논리적으로 삭제합니다.
     * 삭제된 프로필은 더 이상 조회되지 않으며,
     * 실제 데이터베이스에서 제거되지 않고 삭제 플래그가 설정됩니다.
     *
     * @param id 삭제할 프로필의 ID
     * @return 삭제된 프로필 정보를 포함하는 DeleteProfileResponseDto 객체
     * @throws ResponseStatusException 프로필이 존재하지 않거나 이미 삭제된 경우 예외를 발생시킵니다.
     */
    @Transactional
    public DeleteProfileResponseDto deleteProfile(Long id) {
        Profile profile = profileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Profile not found"));

        // 이미 삭제된 프로필이라면 예외를 던짐
        if (profile.getIsDeleted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Profile is already deleted");
        }

        // 논리적 삭제 처리
        profile.setIsDeleted(true);  // 삭제 플래그를 true로 설정
        profile.setDeletedAt(LocalDateTime.now());  // 삭제 일자 기록

        // 삭제된 프로필 저장
        Profile deletedProfile = profileRepository.save(profile);

        // 삭제된 프로필을 DTO로 변환하여 반환
        return DeleteProfileResponseDto.of(deletedProfile);
    }
}