package com.example.ticketbooking.repositories;

import com.example.ticketbooking.entities.ApplicationUser;
import com.example.ticketbooking.services.ApplicationUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<ApplicationUser,Long> {
    public ApplicationUser findByUsername(String username);
    public Boolean existsByUsername(String username);
}
