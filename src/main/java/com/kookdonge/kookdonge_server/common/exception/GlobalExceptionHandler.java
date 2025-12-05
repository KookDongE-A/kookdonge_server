package com.kookdonge.kookdonge_server.common.exception;

import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ResponseDTO<Void>> handleCustomException(CustomException e) {
        log.error("CustomException: {}", e.getMessage());
        return ResponseEntity
                .status(200)
                .body(ResponseDTO.error(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO<Void>> handleException(Exception e) {
        log.error("Unexpected Exception: ", e);
        CustomException customException = new CustomException(e.getMessage(), 500);
        return ResponseEntity
                .status(200)
                .body(ResponseDTO.error(customException));
    }
}
