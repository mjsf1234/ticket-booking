package com.example.ticketbooking.repositories;

import com.example.ticketbooking.entities.ApplicationUser;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AppUserRepository extends JpaRepository<ApplicationUser,Long> {
    public ApplicationUser findByUsername(String username);
    public Boolean existsByUsername(String username);
}
