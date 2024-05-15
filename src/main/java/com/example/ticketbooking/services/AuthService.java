package com.example.ticketbooking.services;

import com.example.ticketbooking.dto.LoginDto;
import com.example.ticketbooking.dto.RegisterDto;

public interface AuthService {

    public String login(LoginDto loginDto);
    public String signup(RegisterDto registerDto);

}
