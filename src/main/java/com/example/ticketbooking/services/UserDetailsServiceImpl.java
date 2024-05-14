package com.example.ticketbooking.services;

import com.example.ticketbooking.entities.UserEntity;
import com.example.ticketbooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside the : loadUserByUsername username : {}", username);
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("user not found :: loadUserByUsername ");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return  user;
    }
}
