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

public class Genre {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
}
