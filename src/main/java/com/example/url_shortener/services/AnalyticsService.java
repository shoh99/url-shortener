package com.example.url_shortener.services;


import com.example.url_shortener.models.ClickAnalytics;
import com.example.url_shortener.repository.ClickRepository;
import com.example.url_shortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AnalyticsService {

    @Autowired
    private ClickRepository clickAnalyticsRepository;
    @Autowired
    private UrlRepository urlRepository;

    @Async
    public void logClick(String shortCode, String ipAddress, String userAgent, String referrer) {
        urlRepository.findByShortCode(shortCode).ifPresent(urlMapping -> {
            ClickAnalytics analytics = new ClickAnalytics();
            analytics.setUrlMapping(urlMapping);
            analytics.setTimestamp(LocalDateTime.now());
            analytics.setIpAddress(ipAddress);
            analytics.setUserAgent(userAgent);
            analytics.setReferrer(referrer);

            clickAnalyticsRepository.save(analytics);
        });
    }
}
