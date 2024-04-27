package com.omega.simpleecommerceapplication.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiException> handleException(ResourceNotFoundException exception,
                                                        HttpServletRequest request) {
        var apiError = new ApiException(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                request.getRequestURI()
                );

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiException> handleException(DuplicateResourceException exception,
                                                        HttpServletRequest request) {
        var apiError = new ApiException(
                exception.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidConfirmationTokenException.class)
    public ResponseEntity<ApiException> handleException(InvalidConfirmationTokenException exception,
                                                        HttpServletRequest request) {
        var apiError = new ApiException(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(NoUpdateDetectedException.class)
    public ResponseEntity<ApiException> handleException(NoUpdateDetectedException exception,
                                                        HttpServletRequest request) {
        var apiError = new ApiException(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductOutOfStockException.class)
    public ResponseEntity<ApiException> handleException(ProductOutOfStockException exception,
                                                        HttpServletRequest request) {
        var apiError = new ApiException(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                request.getRequestURI()
        );

        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }













}
