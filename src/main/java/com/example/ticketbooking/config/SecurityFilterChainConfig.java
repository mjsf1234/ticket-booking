package com.example.ticketbooking.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterChainConfig {

    @Bean
    public SecurityFilterChain securityFilterChain (HttpSecurity httpSecurity) throws Exception {
        httpSecurity.cors(AbstractHttpConfigurer::disable);

        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(
                requestMatcher -> requestMatcher
                        .requestMatchers("/api/auth/login").permitAll()
                        .requestMatchers("/api/auth/signup").permitAll()
                        .anyRequest().authenticated()
//                        .and()
//                        .sessionManagement()
//                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        );
        return httpSecurity.build();

    }
}
