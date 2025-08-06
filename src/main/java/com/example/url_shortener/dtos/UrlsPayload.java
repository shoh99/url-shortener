package com.example.url_shortener.dtos;

import lombok.Data;

@Data
public class UrlsPayload {
    private String longUrl;
    private String shortUrl;
}
