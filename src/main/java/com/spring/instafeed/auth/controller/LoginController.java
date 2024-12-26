package com.spring.instafeed.auth.controller;

import com.spring.instafeed.auth.dto.request.LoginRequestDto;
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
public class LoginController {

    private final AuthService authService;

    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(authService.loginUser(dto));
    }
}
