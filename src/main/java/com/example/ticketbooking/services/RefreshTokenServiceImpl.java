package com.example.ticketbooking.services;

import com.example.ticketbooking.entities.RefreshToken;
import com.example.ticketbooking.repositories.AppUserRepository;
import com.example.ticketbooking.repositories.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;


@Service
@AllArgsConstructor
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService{

    private final AppUserRepository appUserRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    @Override
    public RefreshToken createRefreshToken(String userName) {
        // check if user has token and not expired
        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository.findByAppUser_Username(userName);

        refreshTokenOptional.ifPresent(refreshTokenRepository::delete);

        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(UUID.randomUUID().toString())
                .appUser(appUserRepository.findByUsername(userName))
                .expiryTime(Instant.now().plusMillis(600000)) // 10 minutes
                .build();
        refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    @Override
    public Optional<RefreshToken> findbyToken(String token) {
        return refreshTokenRepository.findByRefreshToken(token);
    }

    @Override
    public RefreshToken verifyTokenExpiry(RefreshToken token) {
        if(token.getExpiryTime().compareTo(Instant.now())<0){
            log.info(" {} : token is expired : {}", "verifyTokenExpiry", token);
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getRefreshToken() + " Refresh token is expired. Please make a new login..!");
        }
        return token;
    }
}
