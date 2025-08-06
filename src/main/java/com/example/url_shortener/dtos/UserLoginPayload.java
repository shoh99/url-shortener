package com.example.url_shortener.dtos;

import lombok.Data;

@Data
public class UserLoginPayload {
    private String email;
    private String password;
}
