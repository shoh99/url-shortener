package com.example.url_shortener.repository;

import com.example.url_shortener.models.ClickAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClickRepository extends JpaRepository<ClickAnalytics, Long> {
}
