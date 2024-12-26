package com.spring.instafeed.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidTokenException extends RuntimeException {

    private final HttpStatus status;

    public InvalidTokenException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
