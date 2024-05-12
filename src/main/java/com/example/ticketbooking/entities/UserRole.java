package com.example.ticketbooking.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "role")
public class UserRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long role_id;
    private String name;
}
