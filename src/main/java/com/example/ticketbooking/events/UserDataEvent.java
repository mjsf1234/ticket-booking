package com.example.ticketbooking.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDataEvent {

    private String username;
    private String email;
    private String password;
    private Long contact_number;
    private String role;
    private Long user_id;

}
