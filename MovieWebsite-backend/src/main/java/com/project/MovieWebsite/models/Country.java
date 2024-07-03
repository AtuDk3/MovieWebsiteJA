package com.project.MovieWebsite.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="tab_country")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="name", nullable= false, length=255)
    private String name;

    @Column(name="is_active")
    private int isActive;
}
