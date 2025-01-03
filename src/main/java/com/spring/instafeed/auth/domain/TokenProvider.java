package com.spring.instafeed.auth.domain;

import com.spring.instafeed.user.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;

@Component
@Slf4j
public class TokenProvider {

    private final SecretKey key;
    private static final long TOKEN_VALID_TIME = 1000L * 60 * 60; // 토큰 유효 기간: 1시간

    public TokenProvider(@Value("${secret-key}") String key) {
        this.key = Keys.hmacShaKeyFor(key.getBytes());
    }

    /**
     * 토큰 생성
     */
    public String createToken(User user) {
        // 현재 시간 생성
        Date now = new Date();

        // 토큰 만료 시간 설정 ( 현재 시간 + 유효 기간)
        Date validity = new Date(now.getTime() + TOKEN_VALID_TIME);

        // 사용자 정보를 가져옴 (id와 email)
        String id = user.getId().toString();
        String email = user.getEmail();

        // JWT 클레임(Claims) 생성
        HashMap<String, String> claimMap = new HashMap<>();
        claimMap.put("userId", id); // 사용자 ID 추가
        claimMap.put("email", email); // 사용자 이메일 추가

        // JWT 생성 및 반환
        return Jwts.builder()
                .claims(claimMap) // 클레임 설정
                .issuedAt(now) // 토큰 발행 시간 설정
                .expiration(validity) // 토큰 만료 시간 설정
                .signWith(key) // 서명 키를 설정하여 무결성 보장
                .compact(); // JWT를 문자열로 압축 및 반환
    }

    /**
     * 토큰에서 사용자 senderProfileId 추출
     */
    public Long getUserId(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.parseLong(claims.get("userId", String.class));
    }

    /**
     * 토큰의 유효성 검사
     */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e) {
            log.info("claims {}", e.getClaims());
            return false;
        }
    }
}