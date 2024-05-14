package com.example.ticketbooking.controllers;

import com.example.ticketbooking.dto.LoginDto;
import com.example.ticketbooking.dto.RegisterDto;
import com.example.ticketbooking.entities.Role;
import com.example.ticketbooking.entities.UserEntity;
import com.example.ticketbooking.repositories.RoleRepository;
import com.example.ticketbooking.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

public class AuthController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/api/auth/login")
    public String loginController(@RequestBody LoginDto loginDto){



    }

    @PostMapping("/api/auth/signup")
    public ResponseEntity<String> signupController(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByUsername(registerDto.getUsername())){
            return new ResponseEntity<>("user is taken", HttpStatus.BAD_REQUEST);
        }
        Role role = roleRepository.findByName("USER");
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        UserEntity user = UserEntity.builder()
                .username(registerDto.getUsername())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .roles(roles)
                .build();
        userRepository.save(user);

        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);

    }
}
