package com.project.MovieWebsite.modules;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tab_genre")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class SocialAccount {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "provider", nullable = false)
    private String provider;

    @Column(name = "provider_id", nullable = false)
    private String providerId;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @Column(name = "user_id", nullable = false)
    private User userId;
}
