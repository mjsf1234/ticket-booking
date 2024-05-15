package com.example.ticketbooking.controllers;

import com.example.ticketbooking.config.AuthenticationConfig;
import com.example.ticketbooking.dto.AuthResponseDto;
import com.example.ticketbooking.dto.LoginDto;
import com.example.ticketbooking.dto.RegisterDto;
import com.example.ticketbooking.entities.Role;
import com.example.ticketbooking.entities.UserEntity;
import com.example.ticketbooking.enums.AuthStatus;
import com.example.ticketbooking.repositories.RoleRepository;
import com.example.ticketbooking.repositories.UserRepository;
import com.example.ticketbooking.services.AuthService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@Slf4j
public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;


    @PostMapping("/api/auth/login")
    public ResponseEntity<AuthResponseDto> loginController(@RequestBody LoginDto loginDto) {

        String jwtToken = authService.login(loginDto);
        AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken, AuthStatus.LOGIN_SUCCESSFULLY);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authResponseDto);
    }
    @PostMapping("/api/auth/signup")
    public ResponseEntity<AuthResponseDto> signupController(@RequestBody RegisterDto registerDto){

        try{
            String jwtToken = authService.signup(registerDto);
            AuthResponseDto authResponseDto = new AuthResponseDto(jwtToken,AuthStatus.USER_CREATED);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(authResponseDto);
        }catch (Exception e){
            AuthResponseDto authResponseDto = new AuthResponseDto(null,AuthStatus.USER_NOT_CREATED);
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(authResponseDto);
        }

    }
}
