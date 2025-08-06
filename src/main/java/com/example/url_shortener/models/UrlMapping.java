package com.example.url_shortener.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="url_mappings")
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "short_code", unique = true)
    private String shortCode;
    @Column(name = "long_url", nullable = false, columnDefinition = "TEXT")
    private String longUrl;
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updateAt;
    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
    @ManyToOne()
    @JoinColumn(name = "users_id")
    private User user;
}
