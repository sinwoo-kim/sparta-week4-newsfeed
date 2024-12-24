package com.spring.instafeed.auth.domain;

import com.spring.instafeed.user.entity.User;
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
        Date now = new Date();
        Date validity = new Date(now.getTime() + TOKEN_VALID_TIME);
        String id = user.getId().toString();
        String email = user.getEmail();

        HashMap<String, String> claimMap = new HashMap<>();
        claimMap.put("userId", id);
        claimMap.put("email", email);

        return Jwts.builder()
                .claims(claimMap)
                .issuedAt(now)
                .expiration(validity)
                .signWith(key)
                .compact();
    }

    /**
     * 토큰에서 사용자 id 추출
     */
    public Long getUserId(String token) {
        return Long.parseLong(
                Jwts.parser()
                        .verifyWith(key)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload()
                        .getSubject()
        );
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