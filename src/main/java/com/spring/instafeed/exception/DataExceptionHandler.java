package com.spring.instafeed.exception;

import com.spring.instafeed.exception.data.DataAlreadyDeletedException;
import com.spring.instafeed.exception.data.DataAlreadyExistsException;
import com.spring.instafeed.exception.data.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class DataExceptionHandler {

    /**
     * 데이터가 이미 삭제된 경우
     * @param ex : DataAlreadyDeletedException
     * @return : Map<String, Object>
     *          - "status": HTTP 상태 코드 (HttpStatus)
     *          - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(DataAlreadyDeletedException.class)
    public ResponseEntity<Map<String, Object>> handleDataAlreadyDeletedException(DataAlreadyDeletedException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.createExceptionResponseBody(ex));
    }

    /**
     * 데이터가 이미 존재하는 경우
     * @param ex : DataAlreadyExistsException
     * @return : Map<String, Object>
     *          - "status": HTTP 상태 코드 (HttpStatus)
     *          - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(DataAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDataAlreadyExistsException(DataAlreadyExistsException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(ex.createExceptionResponseBody(ex));
    }

    /**
     * 데이터를 찾을 수 없는 경우
     * @param ex : DataNotFoundException
     * @return : Map<String, Object>
     *     - "status": HTTP 상태 코드 (HttpStatus)
     *     - "message": 에러 메세지 (String)
     */
    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleDataNotFoundException(DataNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ex.createExceptionResponseBody(ex));
    }
}
