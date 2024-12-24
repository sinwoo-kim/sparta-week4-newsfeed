package com.spring.instafeed.auth.controller;

import com.spring.instafeed.auth.dto.request.LoginRequestDto;
import com.spring.instafeed.auth.dto.response.AccessTokenResponseDto;
import com.spring.instafeed.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class LoginController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AccessTokenResponseDto> login(@RequestBody LoginRequestDto dto) {
        AccessTokenResponseDto accessTokenResponseDto = authService.loginUser(dto);
        String token = accessTokenResponseDto.accessToken();

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .maxAge(60 * 60)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(accessTokenResponseDto);
    }
}
