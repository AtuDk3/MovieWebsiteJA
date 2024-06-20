package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "tab_token")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "token")
    private String token;

    @Column(name = "token_type")
    private String tokenType;

    @Column(name = "expration_date")
    private LocalDateTime exprationDate;

    @Column(name = "revoked")
    private int revoked;

    @Column(name = "expired")
    private int expired;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
