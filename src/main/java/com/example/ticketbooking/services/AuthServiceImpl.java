package com.example.ticketbooking.services;


import com.example.ticketbooking.dto.LoginDto;
import com.example.ticketbooking.dto.RegisterDto;
import com.example.ticketbooking.entities.Role;
import com.example.ticketbooking.entities.UserEntity;
import com.example.ticketbooking.repositories.RoleRepository;
import com.example.ticketbooking.repositories.UserRepository;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(),
                loginDto.getPassword()
        );
        Authentication userAuthentication =authenticationManager.authenticate(authentication);
    }

    @Override
    public String signup(RegisterDto registerDto) {
        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw new RuntimeException("user already exists");
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

    }
}
