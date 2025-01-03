package com.spring.instafeed.exception.common;

import org.springframework.http.HttpStatus;

public record ApiResponse<T>(HttpStatus status, String message, T Data) {

    public static <T> ApiResponse<T> success(HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, message, data);
    }

    public static <T> ApiResponse<T> error(HttpStatus status, String message) {
        return new ApiResponse<>(status, message, null);
    }

}
