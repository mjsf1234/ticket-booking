package com.example.ticketbooking.services;


import com.example.ticketbooking.config.KafkaProducerService;
import com.example.ticketbooking.dto.LoginDto;
import com.example.ticketbooking.dto.RegisterDto;
import com.example.ticketbooking.entities.ApplicationUser;
import com.example.ticketbooking.entities.Role;
import com.example.ticketbooking.events.UserDataEvent;
import com.example.ticketbooking.repositories.AppUserRepository;
import com.example.ticketbooking.repositories.RoleRepository;
import com.example.ticketbooking.util.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@Slf4j
@Service
public class AuthServiceImpl implements AuthService{
    private final AuthenticationManager authenticationManager;
    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final KafkaProducerService kafkaProducerService;
    @Override
    public String login(LoginDto loginDto) {

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword());
        Authentication authenticatedToken = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticatedToken);

        String authenticatedUserName = ((UserDetails)(authenticatedToken.getPrincipal())).getUsername();

        return JwtUtils.generateToken(authenticatedUserName);
    }

    @Override
    public String signup(RegisterDto registerDto) {
        log.info("inside the signup method new  , username :{}",registerDto.getUsername());
        try {
            if(userRepository.existsByUsername(registerDto.getUsername())){
                throw new RuntimeException("user already exists");
            }
            Role role = roleRepository.findByName(registerDto.getRole());
            Set<Role> roles = new HashSet<>();
            roles.add(role);

            ApplicationUser user = ApplicationUser.builder()
                    .username(registerDto.getUsername())
                    .password(passwordEncoder.encode(registerDto.getPassword()))
                    .email(registerDto.getEmail())
                    .mobileNumber(registerDto.getMobileNumber())
                    .roles(roles)
                    .build();
            ApplicationUser createdUser= userRepository.save(user);
            UserDataEvent userDataEvent = UserDataEvent.builder()
                    .user_id(createdUser.getUser_id())
                    .contact_number(createdUser.getMobileNumber())
                    .email(createdUser.getEmail())
                    .password(createdUser.getPassword())
                    .username(createdUser.getUsername())
                    .build();
            log.info("sending message to kafka : {}", userDataEvent);
            kafkaProducerService.sendUserDataEvent(userDataEvent);

            return JwtUtils.generateToken(registerDto.getUsername());
        } catch (Exception e) {
            log.info(String.valueOf(e));
        }
        return null;
    }
}
