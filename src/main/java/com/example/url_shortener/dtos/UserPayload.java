package com.example.url_shortener.dtos;


import lombok.Data;

@Data
public class UserPayload {
    private String username;
    private String email;
    private String password;
}
