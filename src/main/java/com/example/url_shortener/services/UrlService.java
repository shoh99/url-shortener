package com.example.url_shortener.services;


import com.example.url_shortener.dtos.UrlsDto;
import com.example.url_shortener.dtos.UrlsPayload;
import com.example.url_shortener.models.UrlMapping;
import com.example.url_shortener.models.User;
import com.example.url_shortener.repository.UrlRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.sql.exec.ExecutionException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UrlService {

    @Autowired
    private AnalyticsService analyticsService;
    @Autowired
    private UrlRepository urlRepository;
    @Autowired
    private Base62Converter base62Converter;
    @Autowired
    private ModelMapper modelMapper;

    private final String domainAddress = "http://localhost:8081";

    //    /*Creates a new short URL, either by generating one or using custom one
    //    This method is transactional to ensure data integrity
    //    */
    @Transactional
    public UrlsDto createNewUrl(User user, UrlsPayload payload) throws Exception {
        if (payload.getShortUrl() == null || payload.getShortUrl().isEmpty()) {
            return generateAndSaveShortUrl(payload.getLongUrl(), user);
        } else {
            return createCustomShortUrl(payload.getLongUrl(), payload.getShortUrl(), user);
        }
    }


    @Cacheable(value = "urls", key = "#shortCode")
    @Transactional(readOnly = true)
    public String getLongUrl(String shortCode) throws Exception {
        UrlMapping urlMapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new ExecutionException("Url not found for code: " + shortCode));

        if (urlMapping.getExpiresAt() != null && urlMapping.getExpiresAt().isBefore(LocalDateTime.now())) {
            urlRepository.delete(urlMapping);
            throw new Exception("URL has expired for code: " + shortCode);
        }

        return urlMapping.getLongUrl();
    }

    public String getLongUrlAndLogAnalytics(String shortCode, HttpServletRequest request) throws Exception {
        String longUrl = this.getLongUrl(shortCode);

        String ipAddress = request.getRemoteAddr();
        String userAgent = request.getHeader("User-Agent");
        String referrer = request.getHeader("Referer");

        analyticsService.logClick(shortCode, ipAddress, userAgent, referrer);

        return longUrl;
    }

     /**
      Handles the logic for creating a new, system-generated short URL.
     **/
    private UrlsDto generateAndSaveShortUrl(String longUrl, User user) {
        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setUser(user);
        UrlMapping savedUrl = urlRepository.save(urlMapping);
        Long uniqueId = savedUrl.getId();

        String encodedId = base62Converter.encode(uniqueId);
        savedUrl.setShortCode(encodedId);

        UrlMapping findUrl = urlRepository.save(savedUrl);

        return toDto(findUrl);
    }

    /**
     * Handles the logic for custom short URL
     * */
    private UrlsDto createCustomShortUrl(String longUrl, String customShortUrl, User user) throws Exception {
        if (urlRepository.findByShortCode(customShortUrl).isPresent()) {
            throw new Exception("Custom URL " + customShortUrl + " is already in use.");
        }

        UrlMapping urlMapping = new UrlMapping();
        urlMapping.setLongUrl(longUrl);
        urlMapping.setShortCode(customShortUrl);
        urlMapping.setCreatedAt(LocalDateTime.now());
        urlMapping.setUser(user);

        UrlMapping savedUrl = urlRepository.save(urlMapping);
        return toDto(savedUrl);
    }

    private UrlsDto toDto(UrlMapping entity) {
        UrlsDto dto = modelMapper.map(entity, UrlsDto.class);
        dto.setShortCode(domainAddress + "/" + entity.getShortCode());
        return dto;
    }
}
