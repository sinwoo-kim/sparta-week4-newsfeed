package com.spring.instafeed.profile.service;

import com.spring.instafeed.common.BaseEntity;
import com.spring.instafeed.exception.data.DataNotFoundException;
import com.spring.instafeed.exception.data.DataAlreadyDeletedException;
import com.spring.instafeed.exception.data.DataAlreadyExistsException;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.profile.dto.response.*;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    // 속성
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final NewsfeedRepository newsfeedRepository;

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
        User foundUser = userRepository
                .findByIdAndIsDeletedFalse(userId)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        profileRepository
                .findByNickname(nickname)
                .ifPresent(existingNickname -> {
                            throw new DataAlreadyExistsException(
                                    HttpStatus.BAD_REQUEST,
                                    "The requested data already exists"
                            );
                        }
                );

        Profile profileToSave = Profile.create(
                foundUser,
                nickname,
                content,
                imagePath
        );

        Profile savedProfile = profileRepository
                .save(profileToSave);

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
    public ReadProfileResponseDto readProfileById(Long id) {
        Profile foundProfile = profileRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        return ReadProfileResponseDto.toDto(foundProfile);
    }


    @Transactional
    @Override
    public UpdateProfileResponseDto updateProfile(
            Long id,
            String nickname,
            String content,
            String imagePath
    ) {

        Profile foundProfile = profileRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        foundProfile.update(
                nickname,
                content,
                imagePath
        );

        // 프로필 닉네임 변경 시 게시물의 닉네임도 변경하는 구간 시작
        List<Newsfeed> newsfeeds = new ArrayList<>();

        newsfeeds = newsfeedRepository
                .findAllByProfileIdAndIsDeletedFalse(foundProfile.getId())
                .stream()
                .peek(newsfeed ->
                        newsfeed.updateNickname(nickname)
                )
                .toList();
        // 프로필 닉네임 변경 시 게시물의 닉네임도 변경하는 구간 종료

        return UpdateProfileResponseDto.toDto(foundProfile);
    }

    /**
     * 기능
     * 프로필 삭제
     *
     * @param id : 삭제할 프로필의 ID
     */
    @Transactional
    @Override
    public void deleteProfile(Long id) {
        Profile foundProfile = profileRepository
                .findById(id)
                .orElseThrow(
                        () -> new DataNotFoundException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        // 이미 삭제된 프로필인지 확인
        if (foundProfile.getIsDeleted()) {
            throw new DataAlreadyDeletedException(
                    HttpStatus.CONFLICT,
                    "The requested data has already been deleted"
            );
        }

        // 프로필 삭제 처리
        foundProfile.markAsDeleted();

        // 삭제된 프로필이 작성한 게시물도 함께 삭제 처리
        List<Newsfeed> newsfeeds = new ArrayList<>();

        newsfeeds = newsfeedRepository
                .findAllByProfileIdAndIsDeletedFalse(
                        foundProfile.getId()
                );

        newsfeeds.stream()
                .peek(BaseEntity::markAsDeleted)
                .forEach(newsfeed -> {
                        }
                );
    }
}

/*
[게시물의 닉네임 수정에 peek 메서드 적용 전 모습]
newsfeeds = newsfeedRepository
        .findAllByProfileIdAndIsDeletedFalse(foundProfile.getId())
        .stream()
        .map(newsfeed -> {
            newsfeed.updateNickname(nickname);
            return newsfeed;
         })
         .toList();
 */