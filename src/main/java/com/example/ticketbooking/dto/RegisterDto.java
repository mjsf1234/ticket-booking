package com.example.ticketbooking.dto;
import com.example.ticketbooking.enums.Roles;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegisterDto {
    private String username;
    private String password;
    @JsonProperty("contact_number")
    private Long mobileNumber;
    private String email;
    private String role;
}
