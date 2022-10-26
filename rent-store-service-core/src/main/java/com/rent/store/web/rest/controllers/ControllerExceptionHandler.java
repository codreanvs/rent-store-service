package com.rent.store.web.rest.controllers;

import com.rent.store.exceptions.ErrorMessage;
import com.rent.store.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Slf4j
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(
            final ResourceNotFoundException resourceNotFoundException,
            final WebRequest webRequest
    ) {
        log.error("Resource not found.", resourceNotFoundException);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorMessage.builder()
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .timestamp(new Date())
                        .message(resourceNotFoundException.getMessage())
                        .description(webRequest.getDescription(false))
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> globalExceptionHandler(
            final Exception exception, final WebRequest webRequest
    ) {
        log.error("Something went wrong. Please try again.", exception);

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorMessage.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .timestamp(new Date())
                        .message(exception.getMessage())
                        .description(webRequest.getDescription(false))
                        .build()
                );
    }

}
