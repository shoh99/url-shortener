package com.example.url_shortener.controller;

import com.example.url_shortener.dtos.UrlsDto;
import com.example.url_shortener.dtos.UrlsPayload;
import com.example.url_shortener.models.User;
import com.example.url_shortener.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/urls")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/")
    public ResponseEntity<UrlsDto> createShortenedUrl(
            @RequestBody UrlsPayload payload,
            @AuthenticationPrincipal User currentUser
            ) throws Exception {
        UrlsDto response =  urlService.createNewUrl(currentUser, payload);

        return ResponseEntity.ok(response);
    }
}
