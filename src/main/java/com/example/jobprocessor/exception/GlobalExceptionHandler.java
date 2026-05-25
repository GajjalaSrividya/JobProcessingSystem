package com.example.jobprocessor.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.jobprocessor.dto.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(
                    GlobalExceptionHandler.class
            );

    // Validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>
    handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        String message =
                ex.getBindingResult()
                        .getFieldError()
                        .getDefaultMessage();

        logger.error(
                "Validation error: {}",
                message
        );

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        message
                );

        return new ResponseEntity<>(
                error,
                HttpStatus.BAD_REQUEST
        );
    }

 // Job not found exception
    @ExceptionHandler(JobNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleJobNotFoundException(
            JobNotFoundException ex
    ) {

        logger.error(
                "Job not found: {}",
                ex.getMessage()
        );

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        ex.getMessage()
                );

        return new ResponseEntity<>(
                error,
                HttpStatus.NOT_FOUND
        );
    }
    // Generic errors
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleException(
            Exception ex
    ) {

        logger.error(
                "Unexpected exception occurred: {}",
                ex.getMessage()
        );

        ErrorResponse error =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Something went wrong"
                );

        return new ResponseEntity<>(
                error,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}