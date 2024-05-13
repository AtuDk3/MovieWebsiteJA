package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tab_user_vip")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserVIP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private float price;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    @PrePersist
    protected void onCreate(){
        registrationDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate(){
        expirationDate = LocalDateTime.now();
    }

}
