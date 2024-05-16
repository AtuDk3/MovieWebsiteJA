package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "tab_user")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "date_of_birth")
    private Date dob;

    @Column(name = "facebook_account_id")
    private String facebookAccountId;

    @Column(name = "google_account_id")
    private String googleAccountId;

    @ManyToOne
    @JoinColumn(name = "vip_id", nullable = false)
    private UserVIP userVip;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "is_active")
    private int isActive;

}
