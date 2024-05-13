package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tab_role")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;
}
