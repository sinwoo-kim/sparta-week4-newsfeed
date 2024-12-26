package com.spring.instafeed.auth.controller;

import com.spring.instafeed.auth.dto.request.SignUpRequestDto;
import com.spring.instafeed.auth.dto.response.AccessTokenResponseDto;
import com.spring.instafeed.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class SignUpController {

    private final AuthService authService;

    /**
     * 회원 가입
     */
    @PostMapping("/signup")
    public ResponseEntity<AccessTokenResponseDto> signUp(@RequestBody SignUpRequestDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.signUpUser(dto));
    }
}
