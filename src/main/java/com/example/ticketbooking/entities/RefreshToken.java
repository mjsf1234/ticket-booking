//package com.example.ticketbooking.entities;
//
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.Instant;
//
//@Data
//@Entity
//@Table(name = "refresh_token")
//public class RefreshToken {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//    private String token;
//    private Instant expiryTime;
//
//    @OneToOne
//    @JoinColumn(name = "id", referencedColumnName = "user_id")
//    private User
//
//}
