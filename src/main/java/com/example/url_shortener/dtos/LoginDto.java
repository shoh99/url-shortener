package com.example.url_shortener.dtos;


import lombok.Data;

@Data
public class LoginDto {
    private String username;
    private String email;
    private String token;
}
