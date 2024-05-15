package com.example.ticketbooking.dto;

import com.example.ticketbooking.enums.AuthStatus;

public record AuthResponseDto (String jwtToken, AuthStatus authStatus){}