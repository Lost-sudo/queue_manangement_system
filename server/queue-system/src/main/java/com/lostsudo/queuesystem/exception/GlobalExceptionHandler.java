package com.lostsudo.queuesystem.exception;

import com.lostsudo.queuesystem.dto.response.MessageResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TokenRefreshException.class)
    public ResponseEntity<?> handleTokenRefreshException(HttpServletRequest request, HttpServletResponse response, TokenRefreshException ex) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                MessageResponse.builder().message(ex.getMessage()).build()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(HttpServletRequest request, HttpServletResponse response, MethodArgumentNotValidException ex) {
        String error = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining((CharSequence) Collectors.joining(", ")));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse.builder().message(error).build());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflictException(HttpServletRequest request, HttpServletResponse response, ConflictException ex) {
        return  ResponseEntity.status(HttpStatus.CONFLICT).body(MessageResponse.builder().message(ex.getMessage()).build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> handleBadRequestException(HttpServletRequest request, HttpServletResponse response, BadRequestException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(MessageResponse.builder().message(ex.getMessage()).build());
    }
}
