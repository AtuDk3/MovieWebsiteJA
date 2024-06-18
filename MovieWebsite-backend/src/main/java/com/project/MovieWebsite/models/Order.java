package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="tab_order")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @Column(name="order-date")
    private LocalDateTime orderDate;

    @Column(name="price")
    private double price;

    @PrePersist
    protected void onCreate(){
        orderDate= LocalDateTime.now();
    }

}