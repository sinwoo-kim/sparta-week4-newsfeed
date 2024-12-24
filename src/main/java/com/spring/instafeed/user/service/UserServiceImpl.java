package com.spring.instafeed.user.service;

import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.dto.response.UpdateUserResponseDto;
import com.spring.instafeed.user.dto.response.ReadUserResponseDto;
import com.spring.instafeed.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    // 속성
    private final UserRepository userRepository;

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 식별자
     * @return UserResponseDto
     */
    @Override
    public ReadUserResponseDto findById(Long id) {
        // todo
        User foundUser = userRepository.findByIdAndIsDeletedFalse(id)
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
        User foundUser = userRepository.findByIdAndIsDeletedFalse(id)
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
        User foundUser = userRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Id does not exist"
                        )
                );

        foundUser.markAsDeleted();
    }
}