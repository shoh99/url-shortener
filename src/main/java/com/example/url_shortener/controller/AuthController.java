package com.example.url_shortener.controller;


import com.example.url_shortener.dtos.LoginDto;
import com.example.url_shortener.dtos.UserDto;
import com.example.url_shortener.dtos.UserLoginPayload;
import com.example.url_shortener.dtos.UserPayload;
import com.example.url_shortener.services.JwtService;
import com.example.url_shortener.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDto> createNewUser(@RequestBody @Valid UserPayload payload) throws Exception {
        UserDto userDto = userService.createNewUser(payload);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginDto> authUser(@RequestBody @Valid UserLoginPayload payload) throws Exception {
        LoginDto loginDto = userService.getUserLogin(payload.getEmail());

        UserDetails userDetails = userDetailsService.loadUserByUsername(payload.getEmail());
        String token = jwtService.generateToken(userDetails);
        loginDto.setToken(token);

        return ResponseEntity.ok(loginDto);
    }

}
