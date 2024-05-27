package com.example.ticketbooking.services;

import com.example.ticketbooking.entities.ApplicationUser;
import com.example.ticketbooking.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Slf4j
@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("inside the : loadUserByUsername username : {}", username);
        ApplicationUser user = userRepository.findByUsername(username);
        if (user == null) {
            log.info("user not found :: loadUserByUsername ");
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return  new ApplicationUserDetails(user);
    }
}
