package com.spring.instafeed.auth.service;

import com.spring.instafeed.auth.domain.Email;
import com.spring.instafeed.auth.domain.Password;
import com.spring.instafeed.auth.domain.TokenProvider;
import com.spring.instafeed.auth.dto.request.LoginRequestDto;
import com.spring.instafeed.auth.dto.request.SignUpRequestDto;
import com.spring.instafeed.auth.dto.response.AccessTokenResponseDto;
import com.spring.instafeed.auth.repository.UserAuthRepositoryImpl;
import com.spring.instafeed.exception.data.DataAlreadyExistsException;
import com.spring.instafeed.exception.invalid.InvalidPasswordException;
import com.spring.instafeed.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final TokenProvider tokenProvider;
    private final UserAuthRepositoryImpl userAuthRepository;

    /**
     * 회원 가입
     */
    public AccessTokenResponseDto signUpUser(SignUpRequestDto dto) {
        // 이메일 형식 검증
        verifyEmail(dto);

        // 비밀번호 형식 검증
        Password.validatePassword(dto.password());

        // 비밀번호 암호화
        String encryptedPassword = Password.generateEncryptedPassword(dto.password())
                .getEncryptedPassword();

        // 유저 등록
        User user = userAuthRepository.registerUser(new User(dto.name(), dto.email(), encryptedPassword));

        return createToken(user);
    }

    /**
     * 로그인
     */
    public AccessTokenResponseDto loginUser(LoginRequestDto dto) {
        User user = userAuthRepository.findByEmail(dto.email());

        // 비밀번호가 일치하는지 검증
        matchPassword(user.getPassword(), dto.password());

        return createToken(user);
    }

    /**
     * 사용된적이 있는 이메일인지 검증
     */
    private void verifyEmail(SignUpRequestDto dto) {
        Email email = Email.generateEmail(dto.email());

        if (userAuthRepository.existsEmail(email.getEmailText())) {
            throw new DataAlreadyExistsException(HttpStatus.CONFLICT, "Email Already Exists");
        }
    }

    /**
     * 비밀번호 검증
     */
    private void matchPassword(String storagePassword, String rawPassword) {
        if (!Password.generatePassword(storagePassword).matchPassword(rawPassword)) {
            throw new InvalidPasswordException(HttpStatus.UNAUTHORIZED, "The passwords do not match");
        }
    }

    /**
     * 유저 정보를 기반으로 토큰 생성
     */
    private AccessTokenResponseDto createToken(User user) {
        String token = tokenProvider.createToken(user);
        return new AccessTokenResponseDto(token, "Bearer", 3600, "read-write");
    }
}
