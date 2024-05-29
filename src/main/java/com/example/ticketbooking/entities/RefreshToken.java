package com.example.ticketbooking.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.system.ApplicationPid;

import java.time.Instant;

@Data
@Entity
@Table(name = "refresh_token")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;
    private Instant expiryTime;

    @OneToOne
    @JoinColumn(name = "fk_id_user", referencedColumnName = "user_id")
    private ApplicationUser appUser;

}
