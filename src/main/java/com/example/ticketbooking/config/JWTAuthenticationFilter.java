package com.example.ticketbooking.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JWTAuthenticationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String jwtToken = getJwtTokenFromRequest(request);

        // process the token

        // pass to the next filter
        filterChain.doFilter(request,response);
    }

    private String getJwtTokenFromRequest(HttpServletRequest request) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader!=null && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }

        return null;
    }
}
