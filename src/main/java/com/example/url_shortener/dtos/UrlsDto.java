package com.example.url_shortener.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UrlsDto {
    private String shortCode;
    private String longUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
