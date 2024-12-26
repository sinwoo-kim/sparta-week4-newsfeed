package com.spring.instafeed.exception.data;

import com.spring.instafeed.exception.common.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataAlreadyDeletedException extends RuntimeException implements BaseException {

    private final HttpStatus status;

    public DataAlreadyDeletedException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }
}
