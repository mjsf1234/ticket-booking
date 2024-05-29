package com.example.ticketbooking.services;

import com.example.ticketbooking.entities.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    public RefreshToken createRefreshToken(String userName);
    public Optional<RefreshToken> findbyToken(String token);

    public RefreshToken verifyTokenExpiry(RefreshToken token);
}

