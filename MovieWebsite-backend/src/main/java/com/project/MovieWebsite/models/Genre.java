package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;
@Builder
@Entity
@Table(name = "tab_genre")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Genre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    private String slug;

    @Column(name = "is_active")
    private int isActive;


}
