package com.example.ticketbooking.dto;

import com.example.ticketbooking.entities.RefreshToken;
import com.example.ticketbooking.enums.AuthStatus;

public record AuthResponseDto (String jwtToken, String refreshToken, AuthStatus authStatus){}