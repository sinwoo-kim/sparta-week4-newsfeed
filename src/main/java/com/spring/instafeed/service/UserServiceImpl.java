package com.spring.instafeed.service;

import com.spring.instafeed.User;
import com.spring.instafeed.dto.user.response.UserResponseDto;
import com.spring.instafeed.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    // 속성
    private final UserRepository userRepository;

    /**
     * 기능
     * 회원가입
     *
     * @param name     : 사용자 이름
     * @param email    : 사용자 이메일
     * @param password : 사용자 비밀번호
     * @return UserResponseDto
     */
    @Transactional
    @Override
    public UserResponseDto signUp(String name, String email, String password) {
        User user = new User(name, email, password);

        User savedUser = userRepository.save(user);

        return UserResponseDto.toDto(savedUser);
    }

    /**
     * 기능
     * 회원 정보 조회
     *
     * @param id : 조회하려는 사용자의 식별자
     * @return UserResponseDto
     */
    @Transactional(readOnly = true)
    @Override
    public UserResponseDto findById(Long id) {
        User foundUser = userRepository.findByIdOrElseThrow(id);
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
    public UserResponseDto updatePasswordById(Long id, String password) {
        User foundUser = userRepository.findByIdOrElseThrow(id);

        foundUser.update(password);

        return UserResponseDto.toDto(foundUser);
    }
}