package com.spring.instafeed.auth.web.filter;

import com.spring.instafeed.auth.domain.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private static final String[] WHITE_LIST = {"/auth/signup", "/auth/login"};

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = resolveToken(request);

        // 토큰의 유효성 검사
        if (token == null || !tokenProvider.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or missing token");
        }

        Long userId = tokenProvider.getUserId(token);
        String requestURI = request.getRequestURI();

        // 유저 유효성 검사
        if (!userId.equals()) {
            /**
             * URI 에서 {id} 와 검증하는 방법
             * - 의문점
             * 1. URI 와 토큰의 userId 가 같은지 검증하는 방법이 보안상 이슈는 발생하지 않는지?
             * 2. 실무에서 어떤 방식으로 처리하는지?
             * 3. URI 검증 방법 외에 다른 방법은 어떤게 있는지?
             */
        }

        request.setAttribute("userId", userId);

        filterChain.doFilter(request, response);
    }

    /**
     * WHITE_LIST 의 경로는 Filter 를 무시함
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String requestURI = request.getRequestURI();

        for (String pattern : WHITE_LIST) {
            if (PatternMatchUtils.simpleMatch(pattern, requestURI)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Request Header 에서 토큰을 가져옴
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
