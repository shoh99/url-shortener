package com.example.url_shortener.handler;

import com.example.url_shortener.dtos.ErrorResponse;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllException(Exception ex) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponse.setMessage("An unexpected error occurred");
        errorResponse.setTimestamp(LocalDateTime.now());
        errorResponse.setDetails(details);

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
