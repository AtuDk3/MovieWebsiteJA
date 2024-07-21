
package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
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

    @Column(name="trading_code", nullable= false, length=255)
    private String tradingCode;

    @Column(name="email", nullable= false, length=255)
    private String email;

    @Column(name="name", nullable= false, length=255)
    private String name;

    @Column(name="description", nullable= false, length=255)
    private String description;

    @Column(name="position", nullable= false, length=255)
    private String position;

    @Column(name="video", nullable= false, length=255)
    private String video;

    @Column(name="create_at", nullable= false)
    private LocalDateTime createAt;

    @Column(name="expiration_at", nullable= false)
    private LocalDateTime expirationAt;

    @Column(name="number_days", nullable= false)
    private int numberDays;

    @Column(name="amount", nullable= false)
    private double amount;

    @Column(name="is_active")
    private int isActive;

    @Column(name="is_confirm")
    private int isConfirm;


}
