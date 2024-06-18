package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name="tab_ads")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable= false, length=255)
    private String name;

    @Column(name="description", nullable= false, length=255)
    private String description;

    @Column(name="banner_ads", nullable= false)
    private String bannerAds;

    @Column(name="create_at", nullable= false)
    private Date createAt;

    @Column(name="expiration_at", nullable= false)
    private Date expirationAt;

    @Column(name="amount", nullable= false)
    private int amount;

    @Column(name="is_active")
    private int isActive;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }
}
