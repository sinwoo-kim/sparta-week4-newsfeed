package com.spring.instafeed.user.service;

import com.spring.instafeed.user.entity.User;
import com.spring.instafeed.user.dto.response.UpdateUserResponseDto;
import com.spring.instafeed.user.dto.response.UserResponseDto;
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
    public UserResponseDto findById(Long id) {
        User foundUser = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "입력된 id가 존재하지 않습니다. 다시 입력해주세요."
                        )
                );
        return UserResponseDto.toDto(foundUser);
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
        User foundUser = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "입력된 id가 존재하지 않습니다. 다시 입력해주세요."
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
    public void delete(Long id) {

        User foundUser = userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(
                        () -> new RuntimeException("사용자가 조회되지 않습니다."
                        )
                );

        foundUser.markAsDeleted();
    }
}