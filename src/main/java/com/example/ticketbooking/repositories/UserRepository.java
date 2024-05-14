package com.example.ticketbooking.repositories;

import com.example.ticketbooking.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity,Long> {
    public UserEntity findByUsername(String username);
    public Boolean existsByUsername(String username);
}
