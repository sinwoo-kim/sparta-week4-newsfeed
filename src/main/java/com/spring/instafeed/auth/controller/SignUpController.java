package com.spring.instafeed.auth.controller;

import com.spring.instafeed.auth.dto.request.SignUpRequestDto;
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

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class SignUpController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<AccessTokenResponseDto> signUp(@RequestBody SignUpRequestDto dto) {
        AccessTokenResponseDto accessTokenResponseDto = authService.signUpUser(dto);
        String token = accessTokenResponseDto.accessToken();

        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(true)
                .maxAge(60 * 60)
                .build();

        /**
         * Todo: URI 추후에 결정
         * ex)
         * URI location = ServletUriComponentsBuilder
         *                 .fromCurrentRequest()
         *                 .path("") // 경로
         *                 .buildAndExpand() // {} 넣을 값
         *                 .toUri();
         */

        return ResponseEntity.created(URI.create(""))
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(accessTokenResponseDto);
    }
}
