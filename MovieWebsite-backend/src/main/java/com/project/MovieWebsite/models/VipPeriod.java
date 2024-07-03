package com.project.MovieWebsite.models;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tab_vip_period")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class VipPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name="registration_date")
    private LocalDateTime registrationDate;

    @Column(name="expiration_date")
    private LocalDateTime expirationDate;

    @PrePersist
    public void onCreate(){
        registrationDate= LocalDateTime.now();
    }


}