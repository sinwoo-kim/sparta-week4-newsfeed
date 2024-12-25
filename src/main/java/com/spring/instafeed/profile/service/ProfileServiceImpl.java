package com.spring.instafeed.profile.service;

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
public class ProfileServiceImpl implements ProfileService {

    // 속성
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    /**
     * 기능
     * 프로필 생성
     *
     * @param userId   : 프로필을 생성할 사용자의 Id
     * @param nickname : 사용자가 등록하려는 닉네임
     * @return 생성된 프로필에 대한 응답 데이터
     */
    @Transactional
    @Override
    public CreateProfileResponseDto createProfile(
            Long userId,
            String nickname,
            String content,
            String imagePath
    ) {
        // todo
        User foundUser = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        // todo
        profileRepository
                .findByNickname(nickname)
                .ifPresent(existingNickname -> {
                            throw new ResponseStatusException(
                                    HttpStatus.BAD_REQUEST,
                                    "The requested data already exists"
                            );
                        }
                );

        // 새로운 프로필 객체를 생성합니다.
        Profile profileToSave = Profile.create(
                foundUser,
                nickname,
                content,
                imagePath
        );

        // 프로필을 데이터베이스에 저장합니다.
        Profile savedProfile = profileRepository.save(profileToSave);

        // 저장된 프로필을 DTO로 생성 및 반환
        return CreateProfileResponseDto.toDto(savedProfile);
    }

    /**
     * 기능
     * 프로필 목록 조회
     *
     * @return 삭제되지 않은 프로필의 리스트
     */
    @Override
    public List<ReadProfileResponseDto> readAllProfiles() {
        List<Profile> profiles = new ArrayList<>();

        profiles = profileRepository.findAllByIsDeletedFalse();

        List<ReadProfileResponseDto> allProfiles = new ArrayList<>();

        allProfiles = profiles.stream()
                .map(ReadProfileResponseDto::toDto)
                .toList();

        return allProfiles;
    }

    /**
     * ID로 프로필을 조회합니다.
     *
     * @param id : 조회하려는 프로필의 식별자
     * @return 조회된 프로필에 대한 응답 데이터
     * @throws ResponseStatusException 프로필이 존재하지 않으면 예외 발생
     */
    @Override
    public ReadProfileResponseDto findById(Long id) {
        // todo
        Profile foundProfile = profileRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        return ReadProfileResponseDto.toDto(foundProfile);
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
    @Override
    public UpdateProfileResponseDto updateProfile(
            Long id,
            UpdateProfileRequestDto requestDto
    ) {

        // todo
        Profile foundProfile = profileRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        foundProfile.update(requestDto);

        return UpdateProfileResponseDto.toDto(foundProfile);
    }

    /**
     * 기능
     * 프로필 삭제
     *
     * @param id : 삭제할 프로필의 ID
     * @throws ResponseStatusException 프로필이 존재하지 않거나 이미 삭제된 경우 예외 발생
     */
    @Transactional
    @Override
    public void deleteProfile(Long id) {
        // todo
        Profile foundProfile = profileRepository
                .findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        // 이미 삭제된 프로필인지 확인
        // todo
        if (foundProfile.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The requested data has already been deleted"
            );
        }

        // 프로필 삭제 처리
        foundProfile.markAsDeleted();
    }
}