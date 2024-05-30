package com.example.ticketbooking.repositories;

import com.example.ticketbooking.entities.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    public Optional<RefreshToken> findByRefreshToken(String token);

    public Optional<RefreshToken> findByAppUser_Username(String Username);
}
