package com.spring.instafeed.exception.auth;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidPasswordException extends RuntimeException {

    private final HttpStatus status;

    public InvalidPasswordException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
