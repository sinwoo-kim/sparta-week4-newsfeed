package com.spring.instafeed.user.service;

import com.spring.instafeed.base.BaseEntity;
import com.spring.instafeed.newsfeed.entity.Newsfeed;
import com.spring.instafeed.newsfeed.repository.NewsfeedRepository;
import com.spring.instafeed.profile.entity.Profile;
import com.spring.instafeed.profile.repository.ProfileRepository;
import com.spring.instafeed.user.dto.response.ReadUserResponseDto;
import com.spring.instafeed.user.dto.response.UpdateUserResponseDto;
import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    // 속성
    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final NewsfeedRepository newsfeedRepository;

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 식별자
     * @return UserResponseDto
     */
    @Override
    public ReadUserResponseDto readUserById(Long id) {
        // todo
        User foundUser = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        return ReadUserResponseDto.toDto(foundUser);
    }

    /**
     * 기능
     * 비밀번호 수정
     *
     * @param id       : 비밀번호를 수정하려는 사용자의 식별자
     * @param password : 수정하려는 비밀번호
     * @return UserResponseDto
     */
    @Transactional
    @Override
    public UpdateUserResponseDto updatePassword(
            Long id,
            String password
    ) {
        // todo
        User foundUser = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );
        foundUser.update(password);

        return new UpdateUserResponseDto("비밀번호 수정에 성공했습니다.");
    }

    /**
     * 기능
     * 사용자 단건 삭제
     *
     * @param id : 삭제하려는 사용자의 식별자
     */
    @Transactional
    @Override
    public void deleteUser(Long id) {

        // todo
        User foundUser = userRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        // 이미 삭제된 사용자인지 확인
        // todo
        if (foundUser.getIsDeleted()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "The requested data has already been deleted"
            );
        }

        // 사용자 삭제 처리
        foundUser.markAsDeleted();

        //사용자가 작성한 프로필 또한 전부 삭제 처리 시작
        List<Profile> profiles = new ArrayList<>();

        profiles = profileRepository
                .findAllByUserIdAndIsDeletedFalse(
                        foundUser.getId()
                );

        profiles.stream()
                .peek(BaseEntity::markAsDeleted)
                .forEach(profile -> {
                            List<Newsfeed> newsfeeds = newsfeedRepository
                                    .findAllByProfileIdAndIsDeletedFalse(
                                            profile.getId()
                                    );
                            newsfeeds.forEach(BaseEntity::markAsDeleted);
                        }
                );
    }
}
        /*
        [수정 전]
        profiles.stream()
        (1) 스트림 생성: profiles 리스트를 스트림으로 변환
            스트림: 데이터 흐름을 처리하는 객체입
        .peek(profile -> profile.markAsDeleted())
        (2) 각 프로필에 markAsDeleted() 메서드 호출 (사이드 이펙트)
            peek(): 스트림의 각 요소에 작업 수행
            여기서는 'markAsDeleted()'를 호출하여 프로필을 삭제 상태로 변경
        .toList();
        (3) 스트림 결과를 리스트로 수집하는 최종 연산
            현재 반환하는 값이 없으므로 불필요한 호출에 해당함
         */