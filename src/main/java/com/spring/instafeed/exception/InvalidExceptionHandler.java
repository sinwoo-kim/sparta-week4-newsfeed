package com.spring.instafeed.exception;

import com.spring.instafeed.exception.invalid.InvalidEmailException;
import com.spring.instafeed.exception.invalid.InvalidFollowRequestException;
import com.spring.instafeed.exception.invalid.InvalidPasswordException;
import com.spring.instafeed.exception.invalid.InvalidTokenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class InvalidExceptionHandler {

    /**
     * 이메일 검증을 실패한 경우
     * @param ex : InvalidEmailException
     * @return : Map<String, Object>
     *          - "status": HTTP 상태 코드 (HttpStatus)
     *          - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(InvalidEmailException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidEmailException(InvalidEmailException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.createExceptionResponseBody(ex));
    }

    /**
     * 팔로우 요청 검증을 실패 한 경우
     * @param ex : InvalidFollowRequestException
     * @return : Map<String, Object>
     *          - "status": HTTP 상태 코드 (HttpStatus)
     *          - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(InvalidFollowRequestException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFollowRequestException(InvalidFollowRequestException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.createExceptionResponseBody(ex));
    }

    /**
     * 비밀번호 검증을 실패 한 경우
     * @param ex : InvalidPasswordException
     * @return : Map<String, Object>
     *          - "status": HTTP 상태 코드 (HttpStatus)
     *          - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidPasswordException(InvalidPasswordException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.createExceptionResponseBody(ex));
    }

    /**
     * 토큰 검증을 실패한 경우
     * @param ex : InvalidTokenException
     * @return : Map<String, Object>
     *          - "status": HTTP 상태 코드 (HttpStatus)
     *          - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<Map<String, Object>> handleDataNotFoundException(InvalidTokenException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.createExceptionResponseBody(ex));
    }
}
