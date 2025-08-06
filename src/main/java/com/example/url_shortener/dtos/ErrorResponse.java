package com.example.url_shortener.dtos;


import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponse {
    private int statusCode;
    private String message;
    private LocalDateTime timestamp;
    private List<String> details;
}
