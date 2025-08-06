package com.example.url_shortener.services;


import com.example.url_shortener.dtos.LoginDto;
import com.example.url_shortener.dtos.UserDto;
import com.example.url_shortener.dtos.UserLoginPayload;
import com.example.url_shortener.dtos.UserPayload;
import com.example.url_shortener.models.User;
import com.example.url_shortener.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public UserDto createNewUser(UserPayload payload) throws Exception {
        Optional<User> optionalUser =  userRepository.findUserByEmail(payload.getEmail());
        if (optionalUser.isPresent()) {
            throw new Exception("User Already exists");
        }

        User user = new User();
        user.setEmail(payload.getEmail());
        user.setUsername(payload.getUsername());

        user.setCreatedDate(LocalDateTime.now());
        user.setPasswordHash(passwordEncoder.encode(payload.getPassword()));
        userRepository.save(user);

        return toDto(user);
    }

    public LoginDto getUserLogin(String email) throws Exception {
        Optional<User> optionalUser =  userRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new Exception("User Already exists");
        }

        User user = optionalUser.get();
        LoginDto loginDto = new LoginDto();
        loginDto.setUsername(user.getUsername());
        loginDto.setEmail(user.getEmail());

        return loginDto;
    }


    private UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
