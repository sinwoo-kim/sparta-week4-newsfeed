package com.spring.instafeed.exception.invalid;

import com.spring.instafeed.exception.common.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidTokenException extends RuntimeException implements BaseException {

    private final HttpStatus status;

    public InvalidTokenException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
