package com.ngohoainam.music_api.exception;

import com.ngohoainam.music_api.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandle {


    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse<Void>> handlingAppException(AppException e) {

        ErrorCode errorCode = e.getErrorCode();

        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.<Void>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .build()
                );
    }
    @ExceptionHandler (value = AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handlingAccessDeniedException(AccessDeniedException e){
        ErrorCode errorCode = ErrorCode.FORBIDDEN;
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.<Void>builder()
                .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                .build());
    }
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map>> hanlingMethodArgumentNotValidException(MethodArgumentNotValidException e){
        ErrorCode errorCode = ErrorCode.INVALID_REQUEST;
        Map errors = new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(fieldError -> errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
        return ResponseEntity
                .status(errorCode.getStatusCode())
                .body(ApiResponse.<Map>builder()
                        .code(errorCode.getCode())
                        .message(errorCode.getMessage())
                        .result(errors)
                        .build());
    };

}